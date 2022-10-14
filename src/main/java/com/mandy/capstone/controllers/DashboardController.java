package com.mandy.capstone.controllers;

import com.mandy.capstone.entities.User;
import com.mandy.capstone.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private AdminService adminService;
    @GetMapping("/dashboard")
    public String getDashboard(@AuthenticationPrincipal User user, ModelMap model){
        model.put("user", user);
        List<User> allUserAccounts = adminService.getAllUserAccounts();
        return "dashboard";
    }
}
