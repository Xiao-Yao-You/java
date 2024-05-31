package com.hk.jigai.module.infra.framework.web.config;

import com.hk.jigai.framework.swagger.config.HkSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infra 模块的 web 组件的 Configuration
 *
 * @author 恒科技改
 */
@Configuration(proxyBeanMethods = false)
public class InfraWebConfiguration {

    /**
     * infra 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi infraGroupedOpenApi() {
        return HkSwaggerAutoConfiguration.buildGroupedOpenApi("infra");
    }

}
