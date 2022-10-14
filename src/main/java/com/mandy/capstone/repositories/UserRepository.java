package com.mandy.capstone.repositories;

import com.mandy.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
   //this query is only needed if we use fetchtype lazy in udser. if we use eager, the authorities will be loaded with user, so no need for this query.
//    @Query("select u from User u" +
//            " left join fetch u.authorities" +
//            " where u.username = :username" )
//select * from user where username = :username (that we passing in)
//because we named our method findByUsername, Sprign is smart enough to extract username from User that passed in.
    User findByUsername(String username);


}
