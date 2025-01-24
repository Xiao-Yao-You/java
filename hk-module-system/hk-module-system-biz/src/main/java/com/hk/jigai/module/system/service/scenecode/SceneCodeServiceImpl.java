package com.hk.jigai.module.system.service.scenecode;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.exception.ServiceException;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.SceneCodeImportExcelVO;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.SceneCodeImportRespVO;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.SceneCodePageReqVO;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.SceneCodeSaveReqVO;
import com.hk.jigai.module.system.controller.admin.user.vo.user.UserImportRespVO;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.dal.mysql.scenecode.SceneCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        if (dto != null) {
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
        } else if ("1".equals(dto.getUseStatus())) {
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
        if (scenceDto == null || !availableStatus.equals(scenceDto.getStatus())) {
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
        if ("00".equals(type)) {
            LocalDate tomorrow = today.plusDays(1);
            resetDate = formatDate(tomorrow, "yyyyMMdd");
        } else if ("01".equals(type)) {
            LocalDate nextWeek = today.plusWeeks(1);
            LocalDate nextWeekFirstDay = nextWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            resetDate = formatDate(nextWeekFirstDay, "yyyyMMdd");
        } else if ("02".equals(type)) {
            LocalDate nextMonth = today.plusMonths(1);
            LocalDate nextMonthFirstDay = nextMonth.withDayOfMonth(1);
            resetDate = formatDate(nextMonthFirstDay, "yyyyMMdd");
        } else if ("03".equals(type)) {
            LocalDate nextYear = today.plusYears(1);
            LocalDate nextYearFirstDay = nextYear.withDayOfYear(1);
            resetDate = formatDate(nextYearFirstDay, "yyyyMMdd");
        }
        argsLua.add(resetDate);
        //执行Lua脚本
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Integer.class);
        Integer result = redisTemplate.execute(redisScript, keys, Long.valueOf(scenceDto.getStart()), Long.valueOf(scenceDto.getStep()), resetDate);
        //拼接单号并返回
        StringBuffer sb = new StringBuffer(scenceDto.getPrefix());
        if (!"none".equals(scenceDto.getInfix())) {
            sb.append(formatDate(today, scenceDto.getInfix()));
        }
        sb.append(String.format("%0" + scenceDto.getSuffix().length() + "d", result));

        //更新编码使用状态
        scenceDto.setUseStatus(new Integer(1));
        sceneCodeMapper.updateById(scenceDto);
        return sb.toString();
    }

    @Override
    public List<SceneCodeDO> getSceneCodeList() {
        return sceneCodeMapper.selectList();
    }

    @Override
    public List<SceneCodeDO> getDeviceType() {
        return sceneCodeMapper.selectList(new QueryWrapper<SceneCodeDO>().lambda().eq(SceneCodeDO::getCodeType, 1));
    }

    @Override
    public List<SceneCodeDO> getDeviceLabel() {
        return sceneCodeMapper.selectList(new QueryWrapper<SceneCodeDO>().lambda().eq(SceneCodeDO::getCodeType, 3));
    }

    @Override
    @Transactional
    public SceneCodeImportRespVO importSceneCodeList(List<SceneCodeImportExcelVO> list, Boolean updateSupport) {

        //判空
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_LIST_IS_EMPTY);
        }
        SceneCodeImportRespVO respVO = SceneCodeImportRespVO.builder().createList(new ArrayList<>())
                .updateList(new ArrayList<>()).failureList(new LinkedHashMap<>()).build();
        //处理数据
        if (CollectionUtil.isNotEmpty(list)) {
            //校验表单数据是否重复
            List<List<SceneCodeImportExcelVO>> groups = list.stream().collect(Collectors.groupingBy(SceneCodeImportExcelVO::getKeyCode)).values().stream().collect(Collectors.toList());
            for (List<SceneCodeImportExcelVO> group : groups) {
                if (group.size() > 1) {
                    respVO.getFailureList().put(group.get(0).getKeyCode(), "编码重复");
                }
            }
            //遍历处理数据
            list.forEach(item -> {
                if (item.getInfix() == null)
                    item.setInfix("none");
                if (item.getKeyCode() == null) {
                    respVO.getFailureList().put("未知编码", "编码编号不能为空");
                } else {
                    if (item.getDescription() == null)
                        respVO.getFailureList().put(item.getKeyCode(), "编码名称不能为空");
                    if (item.getPrefix() == null)
                        respVO.getFailureList().put(item.getKeyCode(), "前缀不能为空");
                    if (item.getStart() != Math.floor(item.getStart()) || Double.isInfinite(item.getStart()))
                        respVO.getFailureList().put(item.getKeyCode(), "起始值格式不正确");
                    if (item.getStep() != Math.floor(item.getStep()) || Double.isInfinite(item.getStep()))
                        respVO.getFailureList().put(item.getKeyCode(), "步长格式不正确");
                    if (item.getSuffix() == null)
                        respVO.getFailureList().put(item.getKeyCode(), "流水号不能为空");
                }

                if (respVO.getFailureList().size() > 0) {
                    return; //存在错误
                } else {
                    //校验编码是否存在
                    SceneCodeDO sceneCodeDO = sceneCodeMapper.selectOneByKeyCode(item.getKeyCode());
                    if (sceneCodeDO == null) {
                        sceneCodeMapper.insert(BeanUtils.toBean(item, SceneCodeDO.class).setUseStatus(0).setStatus(0));
                        respVO.getCreateList().add(item.getKeyCode());
                        return;
                    }
                    if (!updateSupport) {   //是否支持更新
                        respVO.getFailureList().put(item.getKeyCode(), "单据编码已存在");
                        return;
                    }
                    sceneCodeMapper.updateById(BeanUtils.toBean(item, SceneCodeDO.class).setId(sceneCodeDO.getId()));
                    respVO.getCreateList().add(item.getKeyCode());
                }
            });
        }
        return respVO;
    }

    private static String formatDate(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}