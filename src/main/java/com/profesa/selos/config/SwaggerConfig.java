package com.profesa.selos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiDoc(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Prosefa")
                        .description("Documentaçao da API com JWT")
                        .version("1.0")
                );
    }
}
