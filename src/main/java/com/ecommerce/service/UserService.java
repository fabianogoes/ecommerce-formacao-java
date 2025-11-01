package com.ecommerce.service;

import com.ecommerce.model.User;
import com.ecommerce.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;


    public Iterable<User> findByStatus(User.UserStatus status) {
        System.out.println("Finding users by status: " + status);
        return userRepository.findByStatus(status);
    }

    public Iterable<User> findAll() {
        System.out.println("Finding all users");
        return userRepository.findAll();
    }

    public User save(User active) {
        System.out.println("Saving user: " + active);
        return userRepository.save(active);
    }

    public Optional<User> findById(Long id) {
        System.out.println("Finding user by ID: " + id);
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        System.out.println("Deleting user by ID: " + id);
        userRepository.deleteById(id);
    }

    public long count() {
        return userRepository.count();
    }

    public Optional<User> findByEmail(String email) {
        System.out.println("Finding user by email: " + email);
        return userRepository.findByEmail(email);
    }

    public User updateStatus(Long id, User.UserStatus status) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(status);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
