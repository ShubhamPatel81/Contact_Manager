package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.Forms.ContactForm;
import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.Helper;
import com.example.Contact_manager_web.helper.Message;
import com.example.Contact_manager_web.helper.MessageType;
import com.example.Contact_manager_web.service.ContactService;
import com.example.Contact_manager_web.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

        @Autowired
        private UserService userService;
    // add contact page

    @RequestMapping("/add")
    public  String addContact(Model model){
        ContactForm contactForm = new ContactForm();
//        contactForm.setName("Shubham Patel");
//        contactForm.setFavourate(true);
        model.addAttribute("contactForm", contactForm);

        return  "user/addContact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute  ContactForm contactForm, BindingResult bindingResult, Authentication authentication, HttpSession httpSession){


        //process the form data
        String userName = Helper.getEmailOfLoggedInUser(authentication);
       User user=  userService.getUserByEmail(userName);



       // validate the form same as user Registration
       // TODO
        if (bindingResult.hasErrors()){
            httpSession.setAttribute("message", Message.builder()
                            .content("Contact is Not Added !!!!!")
                            .type(MessageType.red)

                    .build());
            return "/user/addContact";
        }

        // form ---> contact
        Contact contact = new Contact();

        // PROCESS THE Contact Picture



        contact.setName(contactForm.getName());
        contact.setFavourate(contactForm.isFavourate());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        // set contact picture url

        contactService.saveContact(contact);

        System.out.println(
                contactForm
        );


        httpSession.setAttribute("message",
                Message.builder()
                        .content("Your contact Added Successfully")
                        .type(MessageType.blue)
                        .build()
                );

//set the message for display
        return "redirect:/user/contact/add";

    }

}
