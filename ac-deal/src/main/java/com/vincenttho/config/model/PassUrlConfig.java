package com.vincenttho.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @className:com.vincenttho.config.aop.PassUrlConfig
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/7     VincentHo       v1.0.0        create
 */
@PropertySource("classpath:application.yml")
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "login.config")
@Data
public class PassUrlConfig {

    private List<String> passUrlList;

}