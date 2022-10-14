package com.mandy.capstone.service;

import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import com.mandy.capstone.security.CustomSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //to make use of userrepository interface which extend Jparepository and all the database method
    @Autowired
    private UserRepository userRepo;

    //we now have a userRepo to send sql request for a match
    //JpaRepository doesn't have a findByUsername method so we have to create our own. and we need to create it in UserRepository interface. We need the search to return a User object so we need to write a method to return User object.
    //when returning a User object, we need to make our User class compatible with UserDetails. the easiest way is make User implements UserDetails. this is ok but it ties our User objects to Spring security framework, requires our User object to implement all methods in UserDetails like getAuthorities... which we may not need. And it make it harder when we want to switch our security to something else beside spring security. So we can work around by making a CustomSecurityUser that extends User and implements UserDetails. This new call will act like User class, but added spring security framework.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByUsername(username);
       if(user == null)
           throw new UsernameNotFoundException("Username or password is incorrect");
       //CustomSecurityUser implements UserDetails so it satisfy the requirement for return value.
        return new CustomSecurityUser(user);
    }
}
