package com.example.Contact_manager_web.repositories;

import com.example.Contact_manager_web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    //write db related operations

    Optional<User> findByEmail(String email);
}
