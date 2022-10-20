package com.mandy.capstone;

import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.BorrowerRepositories;
import com.mandy.capstone.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class LearningTest {
    @Test
    public void encrypt_password (){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("123");
        System.out.println(encodedPassword);
    }
    @Autowired
    private UserRepository userRepository;



}
