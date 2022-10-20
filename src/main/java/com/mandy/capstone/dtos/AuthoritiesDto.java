package com.mandy.capstone.dtos;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritiesDto {
    private Long authority_id;
    private String authority;
    private UserDto userDto;

    public AuthoritiesDto(Authorities authorities) {
        this.authority_id=authorities.getAuthority_id();
        this.authority= authorities.getAuthority();
    }

    public AuthoritiesDto(String role) {
        this.authority=role;
    }

}
