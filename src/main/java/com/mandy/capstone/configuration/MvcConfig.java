package com.mandy.capstone.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//this config is to set which template will be used when user request that specific endpoint. It can be done by using @GetMapping and return redirect but this way is faster and easier to view for all template.
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/adminhome").setViewName("admindashboard");
        registry.addViewController("/adminnewuser").setViewName("admin-create-user");
        registry.addViewController("/staffhome").setViewName("staffdashboard");
        registry.addViewController("/staffnewuser").setViewName("staff-create-user");
        registry.addViewController("/userprofile").setViewName("userprofile");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/login").setViewName("login");

        //redirect error to error page so user won't see a stack trace page and freak out or some bad people can see our vulnerability.
        registry.addViewController("/error").setViewName("error");
    }

}
