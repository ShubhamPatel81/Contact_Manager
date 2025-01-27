package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.Helper;
import com.example.Contact_manager_web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    // Properly initialize the logger
    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }

        String name = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(name);

        if (user != null) {
            System.out.println(user);
            System.out.println(user.getEmail());
            System.out.println(user.getName());
            model.addAttribute("loggedInUser", user);
            logger.info("USER LOGGED IN  : {}", name);
        }
    }
}
