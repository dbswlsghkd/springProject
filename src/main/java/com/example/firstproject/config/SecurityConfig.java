package com.example.firstproject.config;

import com.example.firstproject.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    // 비밀번호 암호화 하는 방식
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable
        http
                .csrf((auth) -> auth.disable());
        // form 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        // http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        http    //특정한 경로에 허용, 거부 가능(람다식으로 작성해야함)
                .authorizeRequests((auth) -> auth
                        // 정적 리소스 허용 (css, js 등)
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // root, login 페이지에서 특정한 작업을 실행하려고 할 때
                        // 모든 사용자가 로그인 하지 않아도 접근 가능하도록 permitAll
                        .requestMatchers("/", "/login", "/register", "/articles", "/api/register").permitAll()
                        // .requestMatchers("/articles").authenticated()  // 인증된 사용자만 접근 가능
                        .requestMatchers("/parts").hasRole("ADMIN")
                        // 와일드 카드를 통해 여러 유저 접근
                        // .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN")
                        // 그 외 막지 못하는 경우 로그인을 통해 접근할 수 있도록 설정
                        .anyRequest().authenticated()
                );

        // 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 로그인 필터 적용
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        // security seesion
        // http    //특정한 경로에 허용, 거부 가능(람다식으로 작성해야함)
        //         .authorizeRequests((auth) -> auth
        //                 // 정적 리소스 허용 (css, js 등)
        //                 .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
        //                 // root, login 페이지에서 특정한 작업을 실행하려고 할 때
        //                 // 모든 사용자가 로그인 하지 않아도 접근 가능하도록 permitAll
        //                 .requestMatchers("/", "/login", "/register", "/articles", "/api/register").permitAll()
        //                 // .requestMatchers("/articles").authenticated()  // 인증된 사용자만 접근 가능
        //                 .requestMatchers("/parts").hasRole("ADMIN")
        //                 // 와일드 카드를 통해 여러 유저 접근
        //                 // .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN")
        //                 // 그 외 막지 못하는 경우 로그인을 통해 접근할 수 있도록 설정
        //                 .anyRequest().authenticated()
        //         );
        // http
        //         //서비스 거부 메시지가 아닌 로그인 페이지로 넘어감
        //         .formLogin((auth) -> auth
        //                 .loginPage("/")
        //                 .loginProcessingUrl("/login") // 로그인 처리 경로 (POST)
        //                 .successForwardUrl("/articles") // 로그인 성공 시 이동할 페이지
        //                 .failureUrl("/") // 로그인 실패 시 리다이렉트할 URL
        //                 .permitAll()
        //         )
        //         .logout((auth) -> auth.permitAll());  // 로그아웃 허용

        //
        // http
        //         .csrf((auth) -> auth.disable());

        return http.build();
    }
}
