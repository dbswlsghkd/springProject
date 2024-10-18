package com.example.firstproject.entity;

import com.example.firstproject.dto.RegisterDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id // 대표값을 지정 like a 주민등록번호
    @Column(name = "userid", length = 50) // 길이를 50으로 지정
    private String userid;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "psword", length = 100, nullable = false)
    private String psword;

    @Column
    private LocalDateTime in_date;

    @Column
    private String role;

    // 엔티티가 처음 저장되기 전에 regdt에 현재 시각을 자동으로 설정
    @PrePersist
    public void prePersist() {
        this.in_date = this.in_date == null ? LocalDateTime.now() : this.in_date;
        this.role = this.role == null ? "ROLE_USER" : this.role;
    }

    // 회원가입
    public static Users createUsers(RegisterDto dto, String encodedPassword) {
        // 예외 발생
        // 엔티티 생성 및 반환
        return new Users(
                dto.getUserid(),
                dto.getName(),
                encodedPassword, // 암호화된 비밀번호 설정
                dto.getIn_date(),
                dto.getRole()
        );
    }
}
