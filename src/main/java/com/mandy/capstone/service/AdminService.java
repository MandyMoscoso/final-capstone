package com.mandy.capstone.service;

import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepo;

    //this method will only be accessible to the roles specified, can be more than 1 role. this annotation alone won't do anything. need more configurations.
    //the configuration can be found in MethodSecurityConfiguration
    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    public List<User> getAllUserAccounts(){
        return userRepo.findAll();

    }
}
