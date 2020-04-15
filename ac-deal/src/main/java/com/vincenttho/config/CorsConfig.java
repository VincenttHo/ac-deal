package com.vincenttho.config;

/*
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        //允许跨域的域名，可以用*表示允许任何域名使用
                        allowedOrigins("https://festive-bohr-6fea62.netlify.com").
                        //允许任何方法（post、get等）
                        allowedMethods("GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE").
                        //允许任何请求头
		                allowedHeaders("Authorization,Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection,Host,Origin,Pragma,Referer,Sec-Fetch-Dest,Sec-Fetch-Mode,Sec-Fetch-Site,User-Agent").
                        //带上cookie信息
                        allowCredentials(true);
                        //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
//                        exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
            }
        };
    }
}*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Order(0)
public class CorsConfig {

    @Value("${cors.filterpath}")
    private String filterpath;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("https://festive-bohr-6fea62.netlify.com");
        corsConfiguration.addAllowedOrigin("https://ac-exchange.netlify.com");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PATCH");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(filterpath, buildConfig());
        return new CorsFilter(source);
    }
}

