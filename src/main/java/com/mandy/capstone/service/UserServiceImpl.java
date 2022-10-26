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

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowerRepositories borrowerRepository;


    //any time you are saving something to the database you should include the @Transactional annotation which ensures that the transaction that gets opened with your datasource gets resolved
    //this method is to add user


    @Override
    @Transactional
    public List<String> addUser(UserDto newUser) {
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
        userRepository.saveAndFlush(user);
        response.add("login");
        System.out.println(response);
        return response;
    }
    @Override
    @Transactional
    public List<String> addNewAccount(UserDto newUser, String role) {
        List<String> response = new ArrayList<>();
        User user = new User(newUser);
        Authorities authority = new Authorities(role);
        authority.setUser(user);
        user.getAuthorities().add(authority);
        user.getBorrower().setUser(user);
        userRepository.saveAndFlush(user);
        response.add("completed adding user");
        System.out.println(response);
        return response;
    }

    @Override
    @Transactional
    public UserDto getUserByUserId(Long userId) {
        UserDto userDto = new UserDto(userRepository.findUserById(userId));
        return userDto;
    }

    @Override
    @Transactional
    public List<String> testing() {
        List<String> response = new ArrayList<>();
        response.add("login");
        return response;
    }

    @Override
    @Transactional
    public void updateUserById(UserDto updateUser) {
        User user = new User(updateUser);
        Borrower borrower = user.getBorrower();
        borrower.setUser(user);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void adminUpdateUserById(UserDto updateUser, Long userId, String role) {
        User user = new User(updateUser);
        Authorities authorities = (Authorities) userRepository.findUserById(userId).getAuthorities().toArray()[0];
        authorities.setAuthority(role);
        authorities.setUser(user);
        if(user.getBorrower()!=null){
            Borrower borrower = user.getBorrower();
            borrower.setUser(user);
        }
        userRepository.saveAndFlush(user);
    }

}
