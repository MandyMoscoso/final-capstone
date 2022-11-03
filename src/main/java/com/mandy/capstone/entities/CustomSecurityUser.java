package com.mandy.capstone.entities;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Data
public class CustomSecurityUser extends User implements UserDetails {

    //this constructor wil be used in UserDetailsServiceImpl return new CustomSecurityUser(user);
    public CustomSecurityUser(User user) {
        this.setAuthorities(user.getAuthorities());
        this.setId(user.getId());
        this.setPassword(((user.getPassword())));
        this.setUsername(user.getUsername());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        this.setPhonenumber(user.getPhonenumber());
        this.setBorrower(user.getBorrower());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
