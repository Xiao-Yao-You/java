package com.hk.jigai.module.infra.controller.admin.file.vo.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * 多租户配置
 *
 * @author 恒科技改
 */
@Data
@Component
public class MinioProperties {
    @Value("${minioUrl}")
    private String minioUrl;
}
