package com.mandy.capstone.service;

import com.mandy.capstone.entities.User;
import com.mandy.capstone.security.CustomSecurityUser;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    @Transactional
    List<String> addUser(User newUser);
}
