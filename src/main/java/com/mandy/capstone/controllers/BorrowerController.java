package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.AuthoritiesDto;
import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.CustomSecurityUser;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/getuser")
    public UserDto getUser(@AuthenticationPrincipal CustomSecurityUser user){
        //using @AuthenticationPrincipal to ensure user can only view their own profile
        Long userId = user.getId();
        UserDto userDto = userService.getUserByUserId(userId) ;
        userDto.setAuthoritiesDto(null);
        userDto.setPassword(null);
        return userDto;
    }

    @PutMapping("/edituser")
    public List<String> editUser(@RequestBody UserDto userDto, @AuthenticationPrincipal CustomSecurityUser user){
        Long userId = user.getId();
        userDto.setId(userId);
        UserDto savedUser =userService.getUserByUserId(userId) ;
        userDto.setAuthoritiesDto(savedUser.getAuthoritiesDto());
        userDto.getBorrowerDto().setBorrower_id(savedUser.getBorrowerDto().getBorrower_id());
//password doesn't show up in request so if password = null, meaning it is unchanged
        //if ("".equals(userDto.getPassword()): check for both empty and null
        if ("".equals(userDto.getPassword())){
            userDto.setPassword(savedUser.getPassword());
        }else{
            String passHash = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(passHash);
        }
        return userService.updateUserById(userDto);
    }
}
