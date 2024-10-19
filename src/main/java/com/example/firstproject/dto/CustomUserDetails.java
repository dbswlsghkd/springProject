package com.example.firstproject.dto;

import com.example.firstproject.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Slf4j
public class CustomUserDetails implements UserDetails {

    private Users users;

    public CustomUserDetails(Users users) {
        log.info("Inside CustomUserDetails constructor ========> " + users);
        this.users = users;
    }

    // Role 값 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority(){

            @Override
            public String getAuthority() {
                return users.getRole();
            }
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return users.getPsword();
    }

    @Override
    public String getUsername() {
        return users.getUserid();
    }

    @Override
    public boolean isAccountNonExpired() {
        // return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return UserDetails.super.isAccountNonLocked();
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        // return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        // return UserDetails.super.isEnabled();
        return true;
    }
}
