package com.mandy.capstone.security;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomSecurityUser extends User implements UserDetails {

    //this constructor wil be used in UserDetailsServiceImpl return new CustomSecurityUser(user);
    public CustomSecurityUser(User user) {
        this.setAuthorities(user.getAuthorities());
        this.setId(user.getId());
        this.setPassword(((user.getPassword())));
        this.setUsername(user.getUsername());
    }
    //noargs constructor. be here just to avoid bugs. that's how Java is.
    public CustomSecurityUser() {
    }

//    private static final long serialVersionUID = -4325526131047887755L;




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
