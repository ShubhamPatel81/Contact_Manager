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
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order) {

        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
       var pageable= PageRequest.of(page , size,sort);

        return contactRepo.findByNameContaining(name, pageable );
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order) {

        Sort sort = order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page , size,sort);

        return contactRepo.findByEmailContaining(email, pageable );

    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByPhoneNumberContaining(phoneNumber, pageable);
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
}
