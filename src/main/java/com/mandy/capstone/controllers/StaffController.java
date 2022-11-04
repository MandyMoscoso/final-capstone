package com.mandy.capstone.controllers;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import com.mandy.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("staff")
public class StaffController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createuser/{role}")
    public List<String> addAcount(@RequestBody UserDto newUser, @PathVariable String role){
        String passHash = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passHash);
        return userService.staffAddNewAccount(newUser, role);
    }

    @GetMapping("alluser")
    public List <User> showAllUser(){
        return  userRepository.findAll();
    }

    @GetMapping("/getuser/{userId}")
    public UserDto getUser(@PathVariable Long userId){
        UserDto userDto = userService.getUserByUserId(userId) ;
        userDto.setAuthoritiesDto(null);
        userDto.setPassword(null);
        return userDto;
    }
    @PutMapping("/edituser/{role}")
    public List<String>  editUser(@RequestBody UserDto userDto, @PathVariable String role){
        List<String> response =new ArrayList<>();
        Long userId = userDto.getId();
        UserDto savedUser = new UserDto(userRepository.findById(userId).get());
        //this if is to prevent a staff to create admin role or edit an admin profile
        if(savedUser.getAuthoritiesDto().toString().contains("authority=ROLE_ADMIN") || role.equalsIgnoreCase("ROLE_ADMIN")){
            response.add("Staff does not have admin authorization");
            response.add("danger");
            return response;
        }
        //this if statement is needed to avoid user role being switched from borrower to staff/admin and vise versa. if the new role is staffs roles, it may cause db to create a duplicate borrower on borrowers table due to the borrowerdto being null. so if that the case, I will assign the saved borrower on db to the userDto obj.
        if(userDto.getBorrowerDto()!=null){
            userDto.getBorrowerDto().setBorrower_id(savedUser.getBorrowerDto().getBorrower_id());
        }else {
            userDto.setBorrowerDto(savedUser.getBorrowerDto());
        }
////password doesn't show up or pass in request so if password = null, meaning it is unchanged
        if("".equals(userDto.getPassword().trim())){
            userDto.setPassword(savedUser.getPassword());
        }else{
            String passHash = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(passHash);
        }
        return userService.updateUserById(userDto);

    }

}
