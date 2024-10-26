package com.example.firstproject.service;

import com.example.firstproject.entity.Part;
import com.example.firstproject.entity.Users;
import com.example.firstproject.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public Page<Users> index(Pageable pageable) {
        return usersRepository.getAllUsers(pageable);
    }

    public Page<Users> getAllUsers(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        return usersRepository.findBySearch(searchPattern, pageable);
    }
}
