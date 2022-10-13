package com.mandy.capstone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
//UserDetailsService return a UserDetails. so our user class need to be inline with UserDetails in order to use UserDetailsService

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
//                .inMemoryAuthentication()
//                .withUser("mandy")
//                .password("$2a$10$TEMJNBHgr5LWpHbcR9R5WuYdJ7DfUU8ZV9MBnl98fa5wCWVkjlFX6")
//                .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //antMatchers("/admin/**") admin and any sub need the specific role
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().hasAnyRole("USER")
                    .and()
                        .formLogin()
                          .loginPage("/login")
                          .defaultSuccessUrl("/dashboard")
                          .permitAll();


    }
}
