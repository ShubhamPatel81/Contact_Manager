package com.example.Contact_manager_web.controller;

import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private ContactService contactService;
    //get contact
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) {
        System.out.println("Fetching contact with ID: " + contactId);
        return contactService.getById(contactId);
    }




}
