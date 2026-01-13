package com.sangsang.autoblog.infra.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // h2-console의 iframe 허용
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // h2-console 로그인 경로 허용
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // static 자원에 대한 접근 모두 허용
                .requestMatchers("/", "/home", "/healthCheck").permitAll()
                .requestMatchers("/auth/signup", "/auth/signin").permitAll()
                .requestMatchers("/content/preview").permitAll()
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin")
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/auth/signout")
                    .logoutSuccessUrl("/home")
            )
            .sessionManagement(sessionManagement -> sessionManagement
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true) //추가로그인 차단
            );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
