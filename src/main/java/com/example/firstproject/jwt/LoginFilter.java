package com.example.firstproject.jwt;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        String username = req.getParameter("username");
        String password = req.getParameter("psword");

        System.out.println(username + "======> username");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("successful authentication");
        // 응답이 커밋되지 않았는지 확인 후 리다이렉트 처리
        try {
            if (!res.isCommitted()) {
                res.sendRedirect("/articles?loginSuccess=true"); // 로그인 성공 후 이동할 페이지
            }
        } catch (IOException | java.io.IOException e) {
            log.error("Redirection failed: ", e);
            throw new ServletException("Redirection failed", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, java.io.IOException {
        log.info("fail authentication");

        if (!res.isCommitted()) {
            try {
                res.sendRedirect("/login"); // 로그인 실패 후 이동할 페이지
            } catch (IOException | java.io.IOException e) {
                log.error("Redirection failed: ", e);
                throw e; // 예외를 다시 던져 처리
            }
        }
    }
}
