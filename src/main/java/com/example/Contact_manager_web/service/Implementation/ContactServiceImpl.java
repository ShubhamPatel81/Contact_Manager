package com.example.Contact_manager_web.service.Implementation;

import com.example.Contact_manager_web.Exceptions.ResourceNotFoundException;
import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.repositories.ContactRepo;
import com.example.Contact_manager_web.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;
    @Override
    public Contact saveContact(Contact contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);

        return contactRepo.save(contact);


    }

    @Override
    public Contact update(Contact contact) {
        return null;
    }

    @Override
    public List<Contact> getAll() {
        return  contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found with given ID!!!"+id));
    }

    @Override
    public void delete(String id) {
        var contactDelete = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found with given ID!!!"+id));
        contactRepo.delete(contactDelete);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return null;
    }

    @Override
    public List<Contact> getByUserId(String userId) {

        return contactRepo.findByUserId(userId);

    }
}
