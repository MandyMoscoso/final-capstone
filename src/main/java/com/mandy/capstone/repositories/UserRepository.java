package com.mandy.capstone.repositories;

import com.mandy.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   //this query is only needed if we use fetchtype lazy in udser. if we use eager, the authorities will be loaded with user, so no need for this query.
//    @Query("select u from User u" +
//            " left join fetch u.authorities" +
//            " where u.username = :username" )
//select * from user where username = :username (that we passing in)
//because we named our method findByUsername, Sprign is smart enough to extract username from User that passed in.
    User findByUsername(String username);

    Optional<User> findById(Long userId);

    //had an error here: No converter found capable of converting from type [org.springframework.data.jpa.repository.query.AbstractJpaQuery$TupleConverter$TupleBackedMap] to type [com.mandy.capstone.dtos.UserDto]] with root cause.
    //After searching, realized native query doesn't work with DTO because Jpa doesn't know how to map it. There are couple ways to fix but may have to use User class as a fix for now.
    @Query(
            value = "SELECT * FROM users FULL JOIN borrowers ON users.id = borrowers.user_id WHERE users.id = :userId",
            nativeQuery = true)
    User findUserById(Long userId);


}
