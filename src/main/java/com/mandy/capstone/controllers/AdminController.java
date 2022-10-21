package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin/createuser/{role}")
    public List<String> addAcount(@RequestBody UserDto newUser, @PathVariable String role){
        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
        System.out.println(role);
        return userService.addNewAccount(newUser, role);

    }

}
