package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model, HttpSession session, @RequestParam(required = false) Category category) {
        System.out.println("Selected category: " + category);
        model.addAttribute("title", "Super Loja Online");
        model.addAttribute("categories", Category.values());
        model.addAttribute("categoriesFilter", category != null ? category : "ALL");

        // Adicionar usuário logado ao model
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);

        if (session.getAttribute("currentUser") != null) {
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
        }

        if (session.getAttribute("shoppingCart") != null) {
            model.addAttribute("shoppingCart", session.getAttribute("shoppingCart"));
        }

        // Verificar e adicionar dados da visualização do carrinho
        if (session.getAttribute("shoppingCartView") != null) {
            model.addAttribute("shoppingCartView", session.getAttribute("shoppingCartView"));
            model.addAttribute("cartTotalItems", session.getAttribute("cartTotalItems"));
            model.addAttribute("cartTotalValue", session.getAttribute("cartTotalValue"));
        }

        model.addAttribute("products", productService.getAllProducts(category));

        return "index";
    }

}
