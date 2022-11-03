package com.mandy.capstone.service;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.BorrowerRepositories;
import com.mandy.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowerRepositories borrowerRepository;
    @Autowired
    private ValidationService validationService;
    //this method is to add user
    @Override
    @Transactional
    public UserDto getUserByUserId(Long userId) {
        UserDto userDto = new UserDto(userRepository.findUserById(userId));
        return userDto;
    }
    //new user method for user who sign up from registration form
    @Override
    @Transactional
    public List<String> addUser(UserDto newUser) {

        //validate that the required fields are not blank and email is not in use
        List<String> validation = validationService.newAccountGeneralCheck(newUser);
        if(validation.get(0).equalsIgnoreCase("false")){
            return validation;
        }
        List<String> response = new ArrayList<>();
        User user = new User(newUser);
        //add role to authority obj and then add this obj to user so Jpa will save to users and authorities table in 1 run.
        Authorities authority = new Authorities("ROLE_USER");
        //link authority with user to get the correct user_id
        authority.setUser(user);
        user.getAuthorities().add(authority);
        //add Borrower to User obj since this is a borrower register
        Borrower borrower = new Borrower();
        borrower.setUser(user);
        user.setBorrower(borrower);
        userRepository.saveAndFlush(user);
        response.add("User account created successfully");
        response.add("success");
        return response;
    }

    //new user method for users created by admin. This is the only method that can create admin role and only accessible by ADMIN.
    @Override
    @Transactional
    public List<String> adminAddNewAccount(UserDto newUser, String role) {
        //validate that the required fields are not blank and email is not in use
        List<String> validation = validationService.newAccountGeneralCheck(newUser);
        if(validation.get(0).equalsIgnoreCase("false")){
            return validation;
        }
        List<String> response = new ArrayList<>();
        User user = new User(newUser);
        Authorities authority = new Authorities(role);
        authority.setUser(user);
        user.getAuthorities().add(authority);
        user.getBorrower().setUser(user);
        userRepository.saveAndFlush(user);
        response.add("User added successfully");
        response.add("success");
        return response;
    }
    @Override
    @Transactional
    public  List<String> adminUpdateUserById(UserDto updateUser, Long userId, String role) {
        //validate that the required fields are not blank and email is not in use
        List<String> validation = validationService.updateAccountGeneralCheck(updateUser);
        if(validation.get(0).equalsIgnoreCase("false")){
            return validation;
        }
        User savedUser = userRepository.findUserById(userId);
        User user = new User(updateUser);
        Authorities authorities = (Authorities) savedUser.getAuthorities().toArray()[0];
        authorities.setAuthority(role);
        authorities.setUser(user);
        if(user.getBorrower()!=null){
            Borrower borrower = user.getBorrower();
            borrower.setUser(user);
        }
        userRepository.saveAndFlush(user);
        List<String> response = new ArrayList<>();
        response.add("User edited successfully");
        response.add("success");
        return response;
    }
    //if the request to update user info from borrower or staff role passed all validation, then this method will be called.
    @Override
    @Transactional
    public List<String> updateUserById(UserDto updateUser) {
        //validate that the required fields are not blank and email is not in use
        List<String> validation = validationService.updateAccountGeneralCheck(updateUser);
        if(validation.get(0).equalsIgnoreCase("false")){
            return validation;
        }
        User user = new User(updateUser);
        Borrower borrower = user.getBorrower();
        borrower.setUser(user);
        userRepository.saveAndFlush(user);
        List<String> response =new ArrayList<>();
        response.add("User edited successfully");
        response.add("success");
        return response;
    }

    //this is the method to create new user by staff. this method cannot create admin role.
    @Override
    @Transactional
    public List<String> staffAddNewAccount(UserDto newUser, String role) {

        //validate that the required fields are not blank and email is not in use
        List<String> validation = validationService.newAccountGeneralCheck(newUser);
        if(validation.get(0).equalsIgnoreCase("false")){
            return validation;
        }
        List<String> response = new ArrayList<>();
        User user = new User(newUser);
        Authorities authority = new Authorities();
        if(role.equalsIgnoreCase("ROLE_STAFF")){
            authority.setAuthority("ROLE_STAFF");
        } else if (role.equalsIgnoreCase("ROLE_USER")){
            authority.setAuthority("ROLE_USER");
        }
        //if the role passed in from request is not staff or user then return this danger alert and no user should be created.
        else{
            response.add("Cannot create user. Please check your info");
            response.add("danger");
            return response;
        }
        authority.setUser(user);
        user.getAuthorities().add(authority);
        user.getBorrower().setUser(user);
        userRepository.saveAndFlush(user);
        response.add("New user created");
        response.add("success");
        return response;
    }

}
