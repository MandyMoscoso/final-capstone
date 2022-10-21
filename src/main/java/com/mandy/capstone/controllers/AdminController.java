package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin/createuser/{role}")
    //@Responsebody:  annotation can be put on a method and indicates that the return type should be written straight to the HTTP response body (and not placed in a Model, or interpreted as a view name).
    //need it here because I used @Controller vs @RestController
    @ResponseBody
    public List<String> addAcount(@RequestBody UserDto newUser, @PathVariable String role){
        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
        System.out.println(role);
        return userService.addNewAccount(newUser, role);
    }

//I am using MvcConfig for view, so no need for this get request. This is just an alternative way to get the job done. MvcConfig has all view in 1 place so easier to keep track.
//    @GetMapping("/adminnewuser")
//    public String getCreateUserPage(){
//        return "admin-create-user";
//    }
//
//    @GetMapping("/adminhome")
//    public String getAdminHomePage(){
//        return "admindashboard";
//    }

}
