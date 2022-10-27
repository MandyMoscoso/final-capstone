package com.mandy.capstone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //csrf with any method that can make changes like post, put, delete, patch. This line can be replaced with inline csrf in the html file for that method.
//                .csrf().disable()
                //antMatchers("/admin/**") admin and any sub need the specific role
                .authorizeRequests()
                //turn off for testing need to turnon when done
                    .antMatchers("/admin**/**").hasAnyRole("ADMIN")
                    .antMatchers("/staff**/**").hasAnyRole("STAFF","ADMIN")
                    .antMatchers("/register**").permitAll()
                    .antMatchers(HttpMethod.GET, "/css/**", "/javascript/**").permitAll()
                //turn off for testing. need to turn on when done
                    .anyRequest().hasAnyRole("USER","ADMIN","STAFF")
//                      .anyRequest().permitAll()
                    .and()
                        .formLogin()
                          .loginPage("/login")//
  // if DaoAuthenticationProvider below return the valid authProvider, then redirect to default endpoint below. When loading the endpoint, I created dashboard controller to redirect user to the correct page based on their role.
                          .defaultSuccessUrl("/dashboard", true)
                          .permitAll();
//control how many people can log in at the same time. currently set up to 1.
//                .and()
//                .sessionManagement()
//                .maximumSessions(1);
    }

    //this method is called in order to authenticate a user, when a username and password is submitted, a UserdetailsService is called to find the password for that user to see if it is correct. It will also provide some other information about the user, such as the authorities. I used this to verify login info.
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
