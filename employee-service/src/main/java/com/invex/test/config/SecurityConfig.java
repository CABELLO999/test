package com.invex.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuracion basica de seguridad con Spring Security.
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(requests -> requests
                        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/actuator/**").permitAll() //  acceso a Swagger y actuator
                        .anyRequest().authenticated())
                .httpBasic(withDefaults()); // Autenticacion basica
        return http.build();
    }
}
