package com.mandy.capstone.service;

import com.mandy.capstone.dtos.UserDto;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    @Transactional
    List<String> addUser(UserDto newUser);
    @Transactional
    UserDto getUserByUserId(Long userId);
    @Transactional
    List<String> updateUserById(UserDto updateUser);
    @Transactional
    List<String> adminUpdateUserById(UserDto userDto, Long userId, String role);
    @Transactional
    List<String> staffAddNewAccount(UserDto newUser, String role);
    @Transactional
    List<String> adminAddNewAccount(UserDto newUser, String role);
}
