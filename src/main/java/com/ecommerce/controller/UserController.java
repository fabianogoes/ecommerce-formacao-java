package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/user")
    public String dashboard(
            Model model,
            @RequestParam(required = false) User.UserStatus status,
            HttpSession session
    ) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        } else {
            System.out.println("User is logged in as: " + session.getAttribute("currentUser"));
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
        }

        if (model.containsAttribute("adminSection")) {
            model.addAttribute("adminSection", model.getAttribute("adminSection"));
        } else {
            model.addAttribute("adminSection", "user");
        }

        if (model.containsAttribute("user")) {
            model.addAttribute("user", model.getAttribute("user"));
        } else {
            model.addAttribute("user", new User());
        }

        Iterable<User> users;
        if (status != null) {
            users = userService.findByStatus(status);
        } else {
            users = userService.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("statusList", User.UserStatus.values());
        return "admin";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user) {
        System.out.println("Attempting to create user: " + user);

        userService.save(user.active());
        System.out.println("User created successfully: " + user);

        return "redirect:/signin";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Editing user with ID: " + id);

        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            System.out.println("User found: " + user.get());
            redirectAttributes.addFlashAttribute("user", user.get());
        } else {
            System.out.println("User not found with ID: " + id);
        }

        return "redirect:/user";
    }

    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute User user) {
        System.out.println("Saving user: " + user);

        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Deleting user with ID: " + id);

        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            System.out.println("User found: " + user.get());
            redirectAttributes.addFlashAttribute("user", user.get());
            redirectAttributes.addFlashAttribute("adminSection", "userDelete");
        } else {
            System.out.println("User not found with ID: " + id);
        }

        return "redirect:/user";
    }

    @GetMapping("/user/delete/confirmation/{id}")
    public String confirmDeleteUser(@PathVariable Long id) {
        System.out.println("Confirming deletion of user with ID: " + id);
        userService.deleteById(id);
        return "redirect:/user";
    }
}
