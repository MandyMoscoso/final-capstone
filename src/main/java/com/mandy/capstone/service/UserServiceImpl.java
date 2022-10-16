package com.mandy.capstone.service;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import com.mandy.capstone.security.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    //any time you are saving something to the database you should include the @Transactional annotation which ensures that the transaction that gets opened with your datasource gets resolved
    //this method is to add user


    @Override
    @Transactional
    public List<String> addUser(User newUser) {
        List<String> response = new ArrayList<>();
        User user = new User(newUser);
        //add role to authority obj and then add this obj to user so Jpa will save to users and authorities table in 1 run.
        Authorities authority = new Authorities("ROLE_USER");
        //link authority with user to get the correct user_id
        authority.setUser(user);
        user.getAuthorities().add(authority);
        userRepository.saveAndFlush(user);
        response.add("login");
        System.out.println(response);
        return response;
    }

}
