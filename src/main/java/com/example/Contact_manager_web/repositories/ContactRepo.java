package com.example.Contact_manager_web.repositories;

import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // find the contact by user

    //custom finder Method
    List<Contact> findByUser(User user);

    //custom Query method
    @Query("SELECT c From Contact c where c.user.id =:userId")
    List<Contact> findByUserId( @Param("userId") String userId);
}
