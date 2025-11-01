package com.ecommerce.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserTest {

    private User user;
    private String firstName;
    private String lastName;
    private String mail;
    private String phone;
    private String password;
    private boolean newsletter;

    @BeforeEach
    void setUp() {
        System.out.println("Setting up test data...");
        user = new User();
        firstName = "João";
        lastName = "Silva";
        mail = "joao@test.com";
        phone = "123456789";
        password = "password";
        newsletter = true;

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(mail);
        user.setPhone(phone);
        user.setPassword(password);
        user.setNewsletter(newsletter);
    }

    @Test
    void testUserCreation() {
        System.out.println("Testing user creation...");

        Assertions.assertEquals(firstName, user.getFirstName());
        Assertions.assertEquals(lastName, user.getLastName());
        Assertions.assertEquals(mail, user.getEmail());
        Assertions.assertEquals(phone, user.getPhone());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertTrue(user.getNewsletter(), "Newsletter should be true");
        Assertions.assertEquals(User.UserStatus.ACTIVE, user.getStatus());
        Assertions.assertEquals(LocalDateTime.now().toLocalDate(), user.getCreatedAt().toLocalDate(), "Creation date should be today");
    }

    @Test
    void testUserStatusChange() {
        System.out.println("Testing user status changes...");

        Assertions.assertEquals(User.UserStatus.ACTIVE, user.getStatus());

        user.inactive();
        Assertions.assertEquals(User.UserStatus.INACTIVE, user.getStatus());

        user.suspended();
        Assertions.assertEquals(User.UserStatus.SUSPENDED, user.getStatus());

        user.blocked();
        Assertions.assertEquals(User.UserStatus.BLOCKED, user.getStatus());
    }
}
