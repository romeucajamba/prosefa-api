package com.profesa.selos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfig::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/auth/**", "v3/api-docs/**", "swagger-ui/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionmangement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATLES))
                .addFilterBefore(jwtFilter, usernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PaswordEncoder passwordEncoder(){
        return new BycryptPasswordEncoder();
    }
}
