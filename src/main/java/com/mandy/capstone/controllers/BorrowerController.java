package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.AuthoritiesDto;
import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class BorrowerController {
    @Autowired
    private UserService userService;

    @GetMapping("/getuser/{userId}")
    public UserDto getUser(@PathVariable Long userId){
        UserDto userDto = userService.getUserByUserId(userId) ;
        userDto.setAuthoritiesDto(null);
        userDto.setPassword(null);
        System.out.println(userDto);
        return userDto;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PutMapping("/edituser/{userId}")
    public void editUser(@RequestBody UserDto userDto, @PathVariable Long userId){
        UserDto savedUser =userService.getUserByUserId(userId) ;
        userDto.setAuthoritiesDto(savedUser.getAuthoritiesDto());
        userDto.getBorrowerDto().setBorrower_id(savedUser.getBorrowerDto().getBorrower_id());
//password doesn't show up or pass in request so if password = null, meaning it is unchanged
        if(userDto.getPassword()=="" ||userDto.getPassword()==null){
            userDto.setPassword(savedUser.getPassword());
        }else{
            String passHash = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(passHash);
        }
        System.out.println(userId + "   " + userDto );
        userService.updateUserById(userDto);

    }


}
