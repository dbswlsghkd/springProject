package com.example.firstproject.jwt;

import com.example.firstproject.dto.CustomUserDetails;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        log.info(role + "===================> JWTUtill의 role");

        String token = jwtUtil.createJwt(username, role, 60*60*10L);
        res.addHeader("Authorization", "Bearer " + token);

        log.info("token ===============> " + token);
        log.info("successful authentication");
        // 응답이 커밋되지 않았는지 확인 후 리다이렉트 처리
        // try {
        //     if (!res.isCommitted()) {
        //         res.addHeader("Authorization", "Bearer " + token);
        //         res.sendRedirect("/articles"); // 로그인 성공 후 이동할 페이지
        //     }
        // } catch (IOException | java.io.IOException e) {
        //     log.error("Redirection failed: ", e);
        //     throw new ServletException("Redirection failed", e);
        // }
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
