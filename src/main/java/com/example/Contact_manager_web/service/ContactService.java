package com.example.Contact_manager_web.service;

import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Contact> searchByName(String name, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order, User user);

    //get Contact by userid
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);

    public long getTotalContactsByUser(String userId) ;


}
