package com.example.Premind_BE.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DeepLink User Test API")
                        .description("DeepLink 사용자 테스트용 API 명세서입니다.")
                        .version("v1.0.0"));
    }
}
