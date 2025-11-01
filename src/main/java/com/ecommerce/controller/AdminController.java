package com.ecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        } else {
            System.out.println("User is logged in as: " + session.getAttribute("currentUser"));
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
        }

        model.addAttribute("adminSection", "dashboard");
        return "admin";
    }

}
