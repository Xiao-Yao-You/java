package com.hk.jigai.framework.mybatis.core.type;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hk.jigai.framework.common.util.json.JsonUtils;

import java.util.Set;

/**
 * 参考 {@link com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler} 实现
 * 在我们将字符串反序列化为 Set 并且泛型为 String
 *
 *
 * @author 恒科技改
 */
public class JsonStringSetTypeHandler extends AbstractJsonTypeHandler<Object> {

    private static final TypeReference<Set<String>> TYPE_REFERENCE = new TypeReference<Set<String>>(){};

    @Override
    protected Object parse(String json) {
        return JsonUtils.parseObject(json, TYPE_REFERENCE);
    }

    @Override
    protected String toJson(Object obj) {
        return JsonUtils.toJsonString(obj);
    }

}
