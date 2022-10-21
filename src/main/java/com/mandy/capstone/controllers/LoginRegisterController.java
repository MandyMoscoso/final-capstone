package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controler will return the string as template url .
// //@RestController will return string as string.
//@RestController = @Controller and @ResponseBody all at one
@Controller
@RequestMapping
public class LoginRegisterController {
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public List<String> addUser(@RequestBody UserDto newUser){
        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
        return userService.addUser(newUser);
    }






}
