package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.Message;
import com.example.Contact_manager_web.helper.MessageType;
import com.example.Contact_manager_web.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepo userRepo;



    //verify email send on the mail

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession httpSession){

        System.out.println("Email verified!!!");
       User user =   userRepo.findByEmailToken(token).orElse(null);
       if (user!=null){
            //user aaya hai
           if (user.getEmailToken().equals(token)){
               user.setEmailVerified(true);
               user.setEnabled(true);
               userRepo.save(user);

               httpSession.setAttribute("message", Message.builder()
                       .type(MessageType.red)
                       .content("Your Email is verified.You can Proceed!!!")
                       .build()
               );

               return "success_page";
           }
           httpSession.setAttribute("message", Message.builder()
                   .type(MessageType.red)
                   .content("Your Email is not verified!!!")
                   .build()
           );

           return "error_page";

       }
        httpSession.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Your Email is not verified!!!")
                .build());
        return "error_page";
    }
}
