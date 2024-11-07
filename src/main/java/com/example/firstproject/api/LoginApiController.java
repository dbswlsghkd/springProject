package com.example.firstproject.api;
import com.example.firstproject.dto.JwtTokenDto;
import com.example.firstproject.dto.LoginDto;
import com.example.firstproject.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginApiController {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginApiController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        try {
            // 로그인 요청된 아이디와 비밀번호 확인 로그 추가
            log.info("로그인 시도: userid=" + loginDto.getUserid() + ", password=" + loginDto.getPsword());

            // 입력된 아이디와 비밀번호로 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUserid(), loginDto.getPsword());

            // AuthenticationManager를 통해 인증 시도
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증 결과를 확인하기 위한 로그
            log.info("Authentication 성공: " + authentication.isAuthenticated());
            log.info("인증된 사용자: " + authentication.getName());
            log.info("사용자 권한: " + authentication.getAuthorities());

            // JWT 토큰 생성
            String jwtToken = jwtUtil.createJwt(loginDto.getUserid(), "USER_ROLE", 3600000L);
            log.info("생성된 JWT 토큰: " + jwtToken);

            // JWT 토큰을 HttpOnly 쿠키에 저장
            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setHttpOnly(true);  // JavaScript에서 접근할 수 없게 설정
            cookie.setSecure(true);    // HTTPS에서만 전송되도록 설정 (Secure는 https에서만 작동)
            cookie.setPath("/");       // 쿠키가 유효한 경로 설정
            cookie.setMaxAge(3600);    // 쿠키의 만료 시간 설정 (1시간)

            response.addCookie(cookie);  // 응답에 쿠키 추가

            // JWT 토큰 반환 (쿠키에 이미 저장되었기 때문에 클라이언트에서 따로 토큰을 받지 않아도 됨)
            return ResponseEntity.ok("로그인 성공");

            // 생성된 JWT 토큰 반환
            // return ResponseEntity.ok(new JwtTokenDto(jwtToken));

        } catch (AuthenticationException e) {
            // 인증 실패 시 발생한 예외 정보와 함께 로그 추가
            log.error("Authentication 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디와 비밀번호를 확인해주세요.");
        }
    }
}
