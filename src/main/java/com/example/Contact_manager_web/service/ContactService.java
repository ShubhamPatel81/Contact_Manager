package com.example.Contact_manager_web.service;

import com.example.Contact_manager_web.entities.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService
{
    //save Contact
    Contact saveContact(Contact contact);

    //update Contact
    Contact update (Contact contact);

    //get contact
    List<Contact> getAll();

    //get Contact by id
    Contact getById(String id);

    //delete Contact
    void delete(String id);

    //search Contact
    List<Contact> search(String name , String email, String phoneNumber);

    //get Contact by userid
    List<Contact> getByUserId(String userId);
}
