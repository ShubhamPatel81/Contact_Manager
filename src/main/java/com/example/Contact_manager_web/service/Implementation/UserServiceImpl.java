package com.example.Contact_manager_web.service.Implementation;

import com.example.Contact_manager_web.Exceptions.ResourceNotFoundException;
import com.example.Contact_manager_web.entities.User;
import com.example.Contact_manager_web.helper.AppConstants;
import com.example.Contact_manager_web.repositories.UserRepo;
import com.example.Contact_manager_web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        //Password Encoder
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

//        set user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User existingUser = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));

        // Update fields
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setAbout(user.getAbout());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setProfilePic(user.getProfilePic());
        existingUser.setEnabled(user.isEnabled());
        existingUser.setEmailVerified(user.isEmailVerified());
        existingUser.setPhoneVerified(user.isPhoneVerified());
        existingUser.setProvider(user.getProvider());
        existingUser.setProvideId(user.getProvideId());

        // Save the updated user
        User savedUser = userRepo.save(existingUser);
        return Optional.ofNullable(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
        userRepo.delete(user);
    }

    @Override
    public boolean isUserExist(User userId) {
        return userRepo.findById(userId.getUserId()).isPresent();
    }

    @Override
    public boolean isUserExist(String userId) {
        return userRepo.existsById(userId);
    }

    @Override
    public boolean isUserExistByUserEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
