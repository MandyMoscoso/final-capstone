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

//    @Secured({"ROLE_STAFF"})
    @GetMapping("/dashboard")
    public String getDashboard(@AuthenticationPrincipal CustomSecurityUser user, ModelMap model){
        String role = user.getAuthorities().toString();
//        System.out.println("user role " + role.contains("authority=ROLE_ADMIN"));
//        model.put("user", user);
//        List<User> allUserAccounts = adminService.getAllUserAccounts();
//        return "dashboard";

        if(role.contains("authority=ROLE_ADMIN")) return "admindashboard";
        if(role.contains("authority=ROLE_STAFF")) return "staffdashboard";
        return "dashboard";
    }
}
