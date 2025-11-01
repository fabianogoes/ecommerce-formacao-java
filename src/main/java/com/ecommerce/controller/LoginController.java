package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String signIn() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                       HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("Attempting signin for email: " + email);

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            System.out.println("User not found with email: " + email);
            redirectAttributes.addFlashAttribute("error", "E-mail ou senha inválidos");
            return "redirect:/signin";
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            System.out.println("Invalid password for email: " + email);
            redirectAttributes.addFlashAttribute("error", "E-mail ou senha inválidos");
            return "redirect:/signin";
        }

        System.out.println("User logged in successfully: " + user);
        session.setAttribute("currentUser", user);
        redirectAttributes.addFlashAttribute("message", "Login realizado com sucesso!");

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("User logged out");
        session.invalidate();
        return "redirect:/";
    }

}
