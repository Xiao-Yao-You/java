package com.hk.jigai.module.system.service.scenecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.*;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.scenecode.SceneCodeMapper;
import redis.clients.jedis.Jedis;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 单据编码类型配置 Service 实现类
 *
 * @author 恒科信改
 */
@Service
@Validated
public class SceneCodeServiceImpl implements SceneCodeService {

    @Resource
    private SceneCodeMapper sceneCodeMapper;

//    @Autowired
//    private Jedis jedis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Integer createSceneCode(SceneCodeSaveReqVO createReqVO) {
        // 插入
        SceneCodeDO sceneCode = BeanUtils.toBean(createReqVO, SceneCodeDO.class);
        sceneCodeMapper.insert(sceneCode);
        // 返回
        return sceneCode.getId();
    }

    @Override
    public void updateSceneCode(SceneCodeSaveReqVO updateReqVO) {
        // 校验存在
        validateSceneCodeExists(updateReqVO.getId());
        // 更新
        SceneCodeDO updateObj = BeanUtils.toBean(updateReqVO, SceneCodeDO.class);
        sceneCodeMapper.updateById(updateObj);
    }

    @Override
    public void deleteSceneCode(Integer id) {
        // 校验存在
        validateSceneCodeExists(id);
        // 删除
        sceneCodeMapper.deleteById(id);
    }

    private void validateSceneCodeExists(Integer id) {
        SceneCodeDO dto = sceneCodeMapper.selectById(id);
        if (dto == null) {
            throw exception(SCENE_CODE_NOT_EXISTS);
        }else if("02".equals(dto.getStatus())){
            throw exception(SCENE_CODE_IS_IN_USE);
        }
    }

    @Override
    public SceneCodeDO getSceneCode(Integer id) {
        return sceneCodeMapper.selectById(id);
    }

    @Override
    public PageResult<SceneCodeDO> getSceneCodePage(SceneCodePageReqVO pageReqVO) {
        return sceneCodeMapper.selectPage(pageReqVO);
    }

    @Override
    public String increment(SceneCodeDO scenceDto) {
        //校验
        if(scenceDto == null || "01".equals(scenceDto.getStatus())){
            throw exception(SCENE_CODE_NOT_AVAILABLE);
        }
        //准备Lua脚本和参数
        String luaScript = "local key = KEYS[1] " +
                "local dateKey = 'reset:date:' .. key " +
                "local initial = tonumber(ARGV[1]) " +
                "local step = tonumber(ARGV[2]) " +
                "local resetDate = tonumber(ARGV[3]) " +
                "local currentDate = tonumber(redis.call('GET', dateKey) or '0') " +
//                "if currentDate ~= resetDate " +
//                "then   redis.call('SET', dateKey, currentDate)   redis.call('SET', key, initial) end " +
                "redis.call('INCRBY', key, step) " +
                "return redis.call('GET', key)";
        List<String> keys = new ArrayList<>();
        keys.add(scenceDto.getKey());
        List<String> argsLua = new ArrayList<>();
        argsLua.add(String.valueOf(scenceDto.getStart()));
        argsLua.add(String.valueOf(scenceDto.getStep()));
        String type = scenceDto.getType();
        String resetDate = "99990101";
        //编码规则,00:每日重置,01:每周重置,02:每月重置,03:每年重置,04:不重置
        LocalDate today = LocalDate.now();
        if("00".equals(type)){
            LocalDate tomorrow = today.plusDays(1);
            resetDate = formatDate(tomorrow,"yyyyMMdd");
        }else if("01".equals(type)){
            LocalDate nextWeek = today.plusWeeks(1);
            LocalDate nextWeekFirstDay = nextWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            resetDate = formatDate(nextWeekFirstDay,"yyyyMMdd");
        }else if("02".equals(type)){
            LocalDate nextMonth = today.plusMonths(1);
            LocalDate nextMonthFirstDay = nextMonth.withDayOfMonth(1);
            resetDate = formatDate(nextMonthFirstDay,"yyyyMMdd");
        }else if("03".equals(type)){
            LocalDate nextYear = today.plusYears(1);
            LocalDate nextYearFirstDay = nextYear.withDayOfYear(1);
            resetDate = formatDate(nextYearFirstDay,"yyyyMMdd");
        }
        argsLua.add(resetDate);
        //执行Lua脚本
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Integer.class);
        //Integer o = redisTemplate.execute(redisScript, keys, String.valueOf(scenceDto.getStart()),String.valueOf(scenceDto.getStep()),resetDate);
        long result = 1;
        StringBuffer sb = new StringBuffer(scenceDto.getPrefix());
        sb.append(formatDate(today,scenceDto.getInfix()));
        sb.append(String.format("%0"+scenceDto.getSuffix().length()+"d", result));




        String script = "local val = redis.call('GET', KEYS[1])" +
                "  print(val)"+
                " if val then" +
                "  print('bb')"+
                "   return redis.call('INCRBY', KEYS[1], ARGV[1])" +
                "  else" +
                "  print('cc')"+
                "   return redis.call('SET', KEYS[1], ARGV[1])" +
                " end";

        DefaultRedisScript<Integer> redisScript2 = new DefaultRedisScript<>();
        redisScript2.setScriptText(script);
        redisScript2.setResultType(Integer.class);

        Integer result2 = redisTemplate.execute(redisScript2, Collections.singletonList("Income"), "8");


        return sb.toString();
    }

    private static String formatDate(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}