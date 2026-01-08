package com.sangsang.autoblog.infra.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // static 자원에 대한 접근 모두 허용
                .requestMatchers("/src/**").permitAll()
                .requestMatchers("/", "/home", "/healthCheck", "/auth/**").permitAll()
                .requestMatchers("/content/preview").permitAll()
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                // .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
