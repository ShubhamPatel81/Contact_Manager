package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.Helper;
import com.example.Contact_manager_web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // user dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    // user profile
    // user profile page

    @RequestMapping(value = "/profile")
    public String userProfile(Model model,  Authentication authentication) {
        // Pass the Authentication object directly
        return "user/profile";
    }



    // this is html page directory location (not router);

//    user add contact page


//    user edit page


    //user deleter page


}


