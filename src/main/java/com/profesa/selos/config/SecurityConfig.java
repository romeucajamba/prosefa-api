package com.profesa.selos.config;

import org.springframework.context.annotation.Bean;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**", "v3/api-docs/**", "swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionmangement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
