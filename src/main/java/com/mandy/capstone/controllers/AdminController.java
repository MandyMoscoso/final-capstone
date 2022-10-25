package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.AuthoritiesDto;
import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/admin/createuser/{role}")
    @ResponseBody
    //@Responsebody:  annotation can be put on a method and indicates that the return type should be written straight to the HTTP response body (and not placed in a Model, or interpreted as a view name).
    //need it here because I used @Controller vs @RestController
//    @ResponseBody
    public List<String> addAcount(@RequestBody UserDto newUser, @PathVariable String role){
        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
        System.out.println(role);
        return userService.addNewAccount(newUser, role);
    }

      @GetMapping("admin/alluser")
      @ResponseBody
    public List <User> showAllUser(){
        return  userRepository.findAll();
    }


    @DeleteMapping("admin/delete/{ids}")
    public String deleteUsers (@PathVariable String ids){
        System.out.println(ids);
        List<Long> longIds = Arrays
                .stream(ids.split("-"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        userRepository.deleteAllById(longIds);
        return "dashboard";
    }

    @PutMapping("/admin/edituser/{role}")
    @ResponseBody
    public void editUser(@RequestBody UserDto userDto, @PathVariable String role){
        Long userId = userDto.getId();
        UserDto savedUser = new UserDto(userRepository.findById(userId).get());
        //this if statement is needed to avoid user role being switched from borrower to staff/admin and vise versa. if the new role is staffs roles, it may cause db to create a duplicate borrower on borrowers table due to the borrowerdto being null. so if that the case, I will assign the saved borrower on db to the userDto obj.
        if(userDto.getBorrowerDto()!=null){
            userDto.getBorrowerDto().setBorrower_id(savedUser.getBorrowerDto().getBorrower_id());
        }else {
            userDto.setBorrowerDto(savedUser.getBorrowerDto());
        }
////password doesn't show up or pass in request so if password = null, meaning it is unchanged
        if(userDto.getPassword()=="" ||userDto.getPassword()==null){
            userDto.setPassword(savedUser.getPassword());
        }else{
            String passHash = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(passHash);
        }
        userService.adminUpdateUserById(userDto,userId, role);

    }


//I am using MvcConfig for view, so no need for this get request. This is just an alternative way to get the job done. MvcConfig has all view in 1 place so easier to keep track. To use this, need to change to @Controller
//    @GetMapping("/adminnewuser")
//    public String getCreateUserPage(){
//        return "admin-create-user";
//    }




}
