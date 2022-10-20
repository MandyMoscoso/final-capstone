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
    List<String> testing();

    @Transactional
    void updateUserById(UserDto updateUser);
}
