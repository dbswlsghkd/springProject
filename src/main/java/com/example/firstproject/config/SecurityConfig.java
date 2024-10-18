package com.example.firstproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //
        http    //특정한 경로에 허용, 거부 가능(람다식으로 작성해야함)
                .authorizeRequests((auth) -> auth
                        // root, login 페이지에서 특정한 작업을 실행하려고 할 때
                        // 모든 사용자가 로그인 하지 않아도 접근 가능하도록 permitAll
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/parts").hasRole("ADMIN")
                        // 와일드 카드를 통해 여러 유저 접근
                        .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN")
                        // 그 외 막지 못하는 경우 로그인을 통해 접근할 수 있도록 설정
                        .anyRequest().authenticated()
                );


        return http.build();
    }
}
