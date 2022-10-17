package com.mandy.capstone.controllers;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.security.CustomSecurityUser;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
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
    public List<String> addUser(@RequestBody User newUser){
//        System.out.println(newUser);

        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
//        Authorities authority = new Authorities("ROLE_USER");
//        newUser.getAuthorities().add(authority);
//        System.out.println(newUser);
        return userService.addUser(newUser);
//        List<String> supplierNames = Arrays.asList("sup1", "sup2", "sup3");
//        return supplierNames ;
    }
}
