package com.example.Contact_manager_web.service.Implementation;

import com.example.Contact_manager_web.Exceptions.ResourceNotFoundException;
import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.repositories.ContactRepo;
import com.example.Contact_manager_web.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
      var contactOld= contactRepo.findById(contact.getId()).orElseThrow(()->new ResourceNotFoundException("Contact Not Found ContactImpl \"update Method\""));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavourate(contact.isFavourate());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
//        contactOld.setLinks(contact.getLinks() );

        return  contactRepo.save(contactOld);

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
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order, User
                                      user) {

        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
       var pageable= PageRequest.of(page , size,sort);

        return contactRepo.findByUserAndNameContaining(user,name, pageable );
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order, User user) {

        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page , size,sort);

        return contactRepo.findByUserAndEmailContaining(user,email, pageable );

    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order,User user) {
        Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining( user,phoneNumber,pageable);
    }



    @Override
    public List<Contact> getByUserId(String userId) {

        return contactRepo.findByUserId(userId);

    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size,sort);
      return   contactRepo.findByUser(user,pageable);
    }



    // Counting the all the contact

    @Override
    public long getTotalContactsByUser(String userId) {
        long count = contactRepo.countByUserId(userId);
        System.out.println("Total contacts for user " + userId + " = " + count); // Debug log
        return count;
    }
}
