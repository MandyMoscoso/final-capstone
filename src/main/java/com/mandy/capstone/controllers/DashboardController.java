package com.mandy.capstone.controllers;
import com.mandy.capstone.entities.CustomSecurityUser;
import com.mandy.capstone.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private AdminService adminService;

//after log in success, all user will send a get request to /dashboard, based on .defaultSuccessUrl("/dashboard". from there, this controller will check their roles and redirect to their dasdhbaord page that I set up for them.
//@AuthenticationPrincipal this is what thymeleaf send from front end. I will check if the user was logged in with which role then redirect them to the correct page. note that I didn't set up for USER role since they will be on the default dashboard view.
    @GetMapping("/dashboard")
    public String getDashboard(@AuthenticationPrincipal CustomSecurityUser user, ModelMap model){
        String role = user.getAuthorities().toString();
        if(role.contains("authority=ROLE_ADMIN")) return "admindashboard";
        if(role.contains("authority=ROLE_STAFF")) return "staffdashboard";
        return "dashboard";
    }
}
