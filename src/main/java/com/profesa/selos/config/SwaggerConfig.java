package com.profesa.selos.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração da documentação Swagger / OpenAPI com suporte a autenticação JWT.
 *
 * Após iniciar o servidor, a documentação estará disponível em:
 * ➤ http://localhost:8080/swagger-ui/index.html
 */

@Configuration
public class SwaggerConfig {
    // Nome da definição de segurança usada no Swagger
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI apiDoc() {
        return new OpenAPI()
                // Ativa o requisito de segurança global
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))

                // Configura o componente de segurança (JWT Bearer)
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Insira o token JWT no formato: Bearer {seu_token}")
                        )
                )

                // Informações gerais da API
                .info(new Info()
                        .title("API Prosefa")
                        .description("Documentação oficial da API com autenticação JWT.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Prosefa")
                                .email("suporte@prosefa.com")
                                .url("https://prosefa.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                );
    }
}