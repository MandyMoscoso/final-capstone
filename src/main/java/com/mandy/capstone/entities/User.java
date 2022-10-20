package com.mandy.capstone.entities;

import com.mandy.capstone.dtos.AuthoritiesDto;
import com.mandy.capstone.dtos.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(unique = true)
    private String username;
//    @Column
    private String password;

    private String firstname;
    private String lastname;
    private String phonenumber;

    //Because we used @Data annotation. That annocation creates equals and hashcode implementations that include checking the content of the OneToMany collections. This interferes with Hibernate's loading strategy, or something like that. Adding @EqualsAndHashCode.Exclude and @ToString.Exclude will prevent that the OneToMany collections get accessed too early
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    //FetchType: there are EAGER and LAZY. LAZY will not pull the authority. EAGER will. but this is less effective but easier to use. We have to use LAZY in this case to get Authority othrewise log in function will have issue.
    //cascade = CascadeType.ALL mean if we delete the user, all authorities (data from other tables that linked to this user) related to that user will be deleted as well. If we want to keep the authority (data from other tables that linked to this user) related to that user, we will use CascadeType.PERSIST. ALL type could be dangerous if you don't know what will you delete.
    //mappedBy: the name of the column in the other table that mapped to this table. In thsi case, "user" is the name of the column in authorities table that we want to map here.
    //we have Set<Authorities> because this is a One to many relationship. we may have many authorities for this user.
    //in authorities table, it will be the opposite. we only have User user, since the other side of the relationship is Many to one - only 1 user for that authority id.
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Authorities> authorities = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Borrower borrower;

    public User(UserDto userDto) {
        Borrower borrower = new Borrower(userDto);
        Set<Authorities> authority = new HashSet<>((Collection) userDto.getAuthoritiesDto());
        if(userDto.getId()!=null){
            this.setId(userDto.getId());
        }

        this.setPassword(((userDto.getPassword())));
        this.setUsername(userDto.getUsername());
        this.setFirstname(userDto.getFirstname());
        this.setLastname(userDto.getLastname());
        this.setPhonenumber(userDto.getPhonenumber());
        this.setBorrower(borrower);
        this.setAuthorities(authority);
    }
}
