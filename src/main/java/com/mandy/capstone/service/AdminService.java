package com.mandy.capstone.service;

import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepo;

    public List<User> getAllUserAccounts(){
        return userRepo.findAll();

    }
}
