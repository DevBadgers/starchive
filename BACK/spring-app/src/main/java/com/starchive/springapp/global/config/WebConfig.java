package com.starchive.springapp.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final int ONE_HOUR = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Adding CORS mapping");
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://starchive.vercel.app",
                        "https://starchive-back.store"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // OPTIONS 추가
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(ONE_HOUR);
    }
}

