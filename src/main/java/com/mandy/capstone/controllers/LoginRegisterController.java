package com.mandy.capstone.controllers;

import com.mandy.capstone.security.CustomSecurityUser;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class LoginRegisterController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public List<String> addUser(@RequestBody CustomSecurityUser newUser){

        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
        return userService.addUser(newUser);
    }
}
