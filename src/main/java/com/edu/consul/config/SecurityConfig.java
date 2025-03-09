package com.edu.consul.config;

import com.edu.consul.security.JwtAuthorizationFilter;
import com.edu.consul.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil util) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/api/users/register", "/api/auth/login").permitAll().anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().addFilter(new JwtAuthorizationFilter(util));

        return http.build();
    }
}
