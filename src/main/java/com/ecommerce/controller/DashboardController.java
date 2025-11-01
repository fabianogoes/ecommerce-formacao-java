package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderorderService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        } else {
            System.out.println("User is logged in as: " + session.getAttribute("currentUser"));
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
        }

        model.addAttribute("totalProducts", productService.count());
        model.addAttribute("totalUsers", userService.count());
        model.addAttribute("totalOrders", orderorderService.count());
        model.addAttribute("totalSales", orderorderService.totalSales());
        model.addAttribute("recentOrders", orderorderService.recentOrders(5));
        model.addAttribute("topProducts", orderorderService.topProducts(5));
        model.addAttribute("adminSection", "dashboard");
        return "admin";
    }


}
