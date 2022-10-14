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
    //UserDetailsServiceImpl , which implements UserDetailsService has a @Service, so it will be autowired to here.
    @Autowired
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
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //csrf with any method that can make changes like post, put, delete, patch. This line can be replaced with inline csrf in the html file for that method.
//                .csrf().disable()
                //antMatchers("/admin/**") admin and any sub need the specific role
                .authorizeRequests()
                    .antMatchers("/admin**/**").hasAnyRole("ADMIN")
                    .antMatchers("/staff**/**").hasAnyRole("STAFF","ADMIN")
                     .antMatchers("/register").permitAll()
                    .anyRequest().hasAnyRole("USER","ADMIN","STAFF")
                    .and()
                        .formLogin()
                          .loginPage("/login")
//                .successForwardUrl("/dashboard")
                          .defaultSuccessUrl("/dashboard", true)
                          .permitAll();
//                //control how many people can log in at the same time. current set up to 1.
//                .and()
//                .sessionManagement()
//                .maximumSessions(1);
    }
}
