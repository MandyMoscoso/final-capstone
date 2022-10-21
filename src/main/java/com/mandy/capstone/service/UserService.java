package com.mandy.capstone.service;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface UserService {
    @Transactional
    List<String> addUser(UserDto newUser);
    @Transactional
    public List<String> addNewAccount(UserDto newUser, String role) ;
    @Transactional
    UserDto getUserByUserId(Long userId);

    @Transactional
    List<String> testing();

    @Transactional
    void updateUserById(UserDto updateUser);
}
