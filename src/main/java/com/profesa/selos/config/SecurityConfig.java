package com.profesa.selos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe responsável pelas configurações de segurança da aplicação.
 * Aqui definimos:
 *  - quais rotas precisam de autenticação
 *  - quais filtros vão interceptar as requisições
 *  - como a sessão será gerenciada (stateless com JWT)
 *  - como as senhas serão criptografadas
 */

@Configuration // indica que essa é uma classe de configuração
@RequiredArgsConstructor // injeta automaticamente dependências final
@EnableWebSecurity  // ativa o módulo de segurança do Spring
public class SecurityConfig {
    // Nosso filtro customizado que valida o JWT antes de acessar qualquer rota protegida
    private final JwtAuthenticationFilter jwtFilter;

    /**
     * Esse método define TODA a lógica de segurança da API.
     * Ele substitui o antigo WebSecurityConfigurerAdapter.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                /**
                 * DESABILITA o CSRF
                 *  - CSRF é útil em aplicações Web com sessões e cookies.
                 *  - Como usamos JWT (autenticação stateless), não precisamos dele.
                 */
                .csrf(csrf -> csrf.disable())

                /**
                 * CONFIGURA AS ROTAS QUE NÃO PRECISAM DE AUTENTICAÇÃO
                 *  - /api/v1/auth/** → login e registro são públicos
                 *  - /swagger-ui/** → documentação
                 *  - /v3/api-docs/** → JSON da doc do Swagger
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**")
                        .permitAll() // essas rotas não exigem token
                        .anyRequest().authenticated() // todas as outras exigem JWT
                )
                /**
                 * DEFINE QUE A SESSÃO SERÁ STATELESS
                 *  - Significa que o servidor NÃO guarda sessão
                 *  - Cada requisição deve trazer seu JWT
                 */
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /**
                 * ADICIONA O NOSSO FILTRO DE JWT ANTES DO FILTRO PADRÃO DO SPRING
                 *  - O UsernamePasswordAuthenticationFilter é o filtro padrão
                 *  - Precisamos validar o JWT ANTES dele ser executado
                 */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                //Constrói e retorna a cadeia de filtros configurada
        return http.build();
    }

    /**
     * BEAN DE ENCODER DE SENHAS
     *  - BCrypt é o padrão recomendado pelo Spring
     *  - Ele gera hash seguro e único para cada senha
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
