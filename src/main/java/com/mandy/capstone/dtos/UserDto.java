package com.mandy.capstone.dtos;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private Set<AuthoritiesDto> authoritiesDto = new HashSet<>();
    private BorrowerDto borrowerDto;

    //Used native query for findUserById. Native query doesn't work with DTO so need this contructor to convert User to User Dto.
    public UserDto(User user) {
        BorrowerDto borrower = new BorrowerDto(user);
        Set<AuthoritiesDto> authority = new HashSet<>((Collection) user.getAuthorities());
        if(user.getId()!= null){
            this.setId(user.getId());
        }
        this.setPassword(((user.getPassword())));
        this.setUsername(user.getUsername());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        this.setPhonenumber(user.getPhonenumber());
        this.setBorrowerDto(borrower);
        this.setAuthoritiesDto(authority);
    }


}
