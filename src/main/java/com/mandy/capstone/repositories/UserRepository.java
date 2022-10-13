package com.mandy.capstone.repositories;

import com.mandy.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //select * from user where username = :username (that we passing in)
    //because we named our method findByUsername, Sprign is smart enough to extract username from User that passed in.
    User findByUsername(String username);
}
