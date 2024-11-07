package com.example.firstproject.service;

import com.example.firstproject.dto.CustomUserDetails;
import com.example.firstproject.entity.Users;
import com.example.firstproject.repository.RegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userData = registerRepository.findByUserid(username);
        // log.info("방금 찍음 userData = {}", userData);
        if (userData == null) {
            throw new UsernameNotFoundException("User not found" + username);
        }

        // userData.setPsword(bCryptPasswordEncoder.encode(userData.getPsword())); // 인코딩 확인
        log.info(userData +  "===-->useData");
        return new CustomUserDetails(userData);
    }


}
