package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.Forms.ContactForm;
//import com.example.Contact_manager_web.Forms.ContactSearchForm;
import com.example.Contact_manager_web.Forms.ContactSearchForm;
import com.example.Contact_manager_web.config.AppConfig;
import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.AppConstants;
import com.example.Contact_manager_web.helper.Helper;
import com.example.Contact_manager_web.helper.Message;
import com.example.Contact_manager_web.helper.MessageType;
import com.example.Contact_manager_web.service.ContactService;
import com.example.Contact_manager_web.service.ImageService;
import com.example.Contact_manager_web.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    private Logger logger =  LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactService contactService;

        @Autowired
        private UserService userService;
    // add contact page

    @Autowired
    private ImageService imageService;



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

            //checking the error by printing
            //bindingResult.getAllErrors().forEach(error->logger.info(error.toString()));

            httpSession.setAttribute("message", Message.builder()
                            .content("Contact is Not Added !!!!!")
                            .type(MessageType.red)

                    .build());
            return "/user/addContact";
        }

        // form ---> contact
        Contact contact = new Contact();

        // PROCESS THE Contact Picture
        logger.info("file information: {}", contactForm.getContactImage().getOriginalFilename());

        //String fileName= UUID.randomUUID().toString();
//        String fileURL =  imageService.uploadImage(contactForm.getContactImage(),fileName);

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
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }

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



    // view contact with pagination and sorting
    @GetMapping()
    public String viewContact(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication){

      String userName= Helper.getEmailOfLoggedInUser(authentication);
      User user= userService.getUserByEmail(userName);
      Page<Contact> pageContactList= contactService.getByUser(user,page,size, sortBy,direction);

         model.addAttribute("contact", pageContactList );
            model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/contacts";
    }

    // search handler
    @GetMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
                                @RequestParam(value = "size",required = false, defaultValue = 9+"") Integer size,
                                @RequestParam(value = "page",required = false, defaultValue = "0")Integer page,
                                @RequestParam(value = "sortBy",required = false, defaultValue = "name") String sortBy,
                                @RequestParam(value = "direction",required = false, defaultValue = "asc") String direction,
                                Model model,
                                Authentication authentication) {
        if (size == null){
            size =10;
            System.out.println("Pointer came here up to '/search' router and size ");
        }

        Page<Contact> pageContact = null;

        var user= userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        if (contactSearchForm.getField().equalsIgnoreCase("byName")) {
            pageContact = contactService.searchByName(contactSearchForm.getKeyword(), size, page, sortBy, direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("byEmail")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getKeyword(), size, page, sortBy, direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("byPhoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getKeyword(), size, page, sortBy, direction,user);
        }

//        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("contact", pageContact);
//        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
//        model.addAttribute("loggedInUser", true);
        return "user/search";
    }




}
