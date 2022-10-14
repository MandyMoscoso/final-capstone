package com.mandy.capstone.service;

import com.mandy.capstone.repositories.UserRepository;
import com.mandy.capstone.security.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    //any time you are saving something to the database you should include the @Transactional annotation which ensures that the transaction that gets opened with your datasource gets resolved
    //this method is to add user


    @Transactional
    public List<String> addUser(CustomSecurityUser newUser) {
        List<String> response = new ArrayList<>();
        CustomSecurityUser user = new CustomSecurityUser(newUser);
        //this step is where a user is actually people persisted***
        userRepository.saveAndFlush(user);
        response.add("/login");
        return response;
    }

}
