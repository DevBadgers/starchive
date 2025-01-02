package com.starchive.springapp.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "https://starchive-back.store", description = "기본 서버 주소")
        , @Server(url = "http://localhost:8080", description = "로컬 주소")})
public class SwaggerConfig {
}
