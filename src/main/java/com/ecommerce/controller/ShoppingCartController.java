package com.ecommerce.controller;

import com.ecommerce.model.CartItemView;
import com.ecommerce.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/add")
    public String addToCart(Model model, @RequestParam Long productId, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        } else {
            System.out.println("User is logged in as: " + session.getAttribute("currentUser"));
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
        }

        if (!shoppingCartService.existProductById(productId)) {
            System.out.println("Product with ID " + productId + " not found.");
            return "redirect:/";
        }

        session.setAttribute("shoppingCart", shoppingCartService.createShoppingCart(productId, session));
        return "redirect:/";
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        }

        // Agrupar produtos por ID e calcular quantidades
        if (session.getAttribute("shoppingCart") == null) {
            System.out.println("Shopping cart is empty.");
            return "redirect:/";
        }

        ArrayList<CartItemView> shoppingCart = shoppingCartService.shoppingCartView(session);
        Double totalValue = shoppingCartService.getSubtotalByCart(shoppingCart);

        // Salvar dados na sessão
        session.setAttribute("shoppingCartView", shoppingCart);
        session.setAttribute("cartTotalItems", shoppingCart.size());
        session.setAttribute("cartTotalValue", totalValue);

        return "redirect:/";
    }



}
