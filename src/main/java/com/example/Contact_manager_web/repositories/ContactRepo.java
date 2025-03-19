package com.example.Contact_manager_web.repositories;

import com.example.Contact_manager_web.entities.Contact;
import com.example.Contact_manager_web.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ContactRepo extends JpaRepository<Contact, String> {

    // find the contact by user

    //custom finder Method
    Page<Contact> findByUser(User user, Pageable pageable);

    //custom Query method
    @Query("SELECT c From Contact c where c.user.id =:userId")
    List<Contact> findByUserId(@Param("userId") String userId);


    Page<Contact> findByUserAndNameContaining(User user, String byName, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String byEmail, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneNumber, Pageable pageable);


    // Count total contacts for a specific user
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.user.userId = :userId")
    long countByUserId(@Param("userId") String userId);
}
