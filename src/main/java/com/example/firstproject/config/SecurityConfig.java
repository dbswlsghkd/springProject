package com.example.firstproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화 하는 방식
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //
        http    //특정한 경로에 허용, 거부 가능(람다식으로 작성해야함)
                .authorizeRequests((auth) -> auth
                        // 정적 리소스 허용 (css, js 등)
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // root, login 페이지에서 특정한 작업을 실행하려고 할 때
                        // 모든 사용자가 로그인 하지 않아도 접근 가능하도록 permitAll
                        .requestMatchers("/", "/login", "/register", "/articles").permitAll()
                        // .requestMatchers("/articles").authenticated()  // 인증된 사용자만 접근 가능
                        .requestMatchers("/parts").hasRole("ADMIN")
                        // 와일드 카드를 통해 여러 유저 접근
                        .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN")
                        // 그 외 막지 못하는 경우 로그인을 통해 접근할 수 있도록 설정
                        .anyRequest().authenticated()
                );
        http
                //서비스 거부 메시지가 아닌 로그인 페이지로 넘어감
                .formLogin((auth) -> auth
                        .loginPage("/")
                        .loginProcessingUrl("/login") // 로그인 처리 경로 (POST)
                        .defaultSuccessUrl("/articles", true)  // 로그인 성공 후 articles로 리다이렉트
                        .permitAll()
                )
                .logout((auth) -> auth.permitAll());  // 로그아웃 허용

        http
                .csrf((auth) -> auth.disable());

        return http.build();
    }
}
