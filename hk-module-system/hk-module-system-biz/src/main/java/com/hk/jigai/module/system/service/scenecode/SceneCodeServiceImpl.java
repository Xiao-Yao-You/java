package com.hk.jigai.module.system.service.scenecode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.*;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.dal.mysql.scenecode.SceneCodeMapper;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Integer createSceneCode(SceneCodeSaveReqVO createReqVO) {
        // 插入
        SceneCodeDO sceneCode = BeanUtils.toBean(createReqVO, SceneCodeDO.class);
        SceneCodeDO dto = sceneCodeMapper.selectOneByKeyCode(createReqVO.getKeyCode());
        if(dto!=null){
            throw exception(SCENE_CODE_ALREADY_EXIST);
        }
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
        }else if("1".equals(dto.getUseStatus())){
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
    public String increment(String keyCode) {
        SceneCodeDO scenceDto = sceneCodeMapper.selectOneByKeyCode(keyCode);
        //校验
        Integer availableStatus = new Integer(0);
        if(scenceDto == null || !availableStatus.equals(scenceDto.getStatus())){
            throw exception(SCENE_CODE_NOT_AVAILABLE);
        }
        //准备Lua脚本和参数
        String luaScript = "local key = KEYS[1] " +
                " local dateKey = 'reset:date:' .. key " +
                " local initial = tonumber(ARGV[1]) " +
                " local step = tonumber(ARGV[2]) " +
                " local resetDate = tonumber(ARGV[3]) " +
                " local currentDate = tonumber(redis.call('GET', dateKey) or '0') " +
                " if currentDate ~= resetDate " +
                " then   redis.call('SET', dateKey, ARGV[3])   redis.call('SET', key, initial) " +
                " else redis.call('INCRBY', key, step) end " +
                " return redis.call('GET', key)";
        List<String> keys = new ArrayList<>();
        keys.add(scenceDto.getKeyCode());
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
        Integer result = redisTemplate.execute(redisScript, keys, Long.valueOf(scenceDto.getStart()),Long.valueOf(scenceDto.getStep()),resetDate);
        //拼接单号并返回
        StringBuffer sb = new StringBuffer(scenceDto.getPrefix());
        if(!"none".equals(scenceDto.getInfix())){
            sb.append(formatDate(today,scenceDto.getInfix()));
        }
        sb.append(String.format("%0"+scenceDto.getSuffix().length()+"d", result));

        //更新编码使用状态
        scenceDto.setUseStatus(new Integer(1));
        sceneCodeMapper.updateById(scenceDto);
        return sb.toString();
    }

    private static String formatDate(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}