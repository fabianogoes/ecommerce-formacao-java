package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public String product(
            Model model,
            @RequestParam(required = false) Category category,
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
            model.addAttribute("adminSection", "product");
        }

        if (model.containsAttribute("product")) {
            model.addAttribute("product", model.getAttribute("product"));
        } else {
            model.addAttribute("product", new Product());
        }

        Iterable<Product> products = productService.getAllProducts(category);

        model.addAttribute("categoriesFilter", category != null ? category : "ALL");
        model.addAttribute("categories", Category.values());
        model.addAttribute("products", products);
        return "admin";
    }

    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute Product product) {
        System.out.println("Saving product: " + product);
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Editing product with ID: " + id);

        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            System.out.println("Product found: " + product.get());
            redirectAttributes.addFlashAttribute("product", product.get());
        } else {
            System.out.println("Product not found with ID: " + id);
        }

        return "redirect:/product";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Deleting product with ID: " + id);

        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            System.out.println("Product found: " + product.get());
            redirectAttributes.addFlashAttribute("product", product.get());
            redirectAttributes.addFlashAttribute("adminSection", "productDelete");
        } else {
            System.out.println("Product not found with ID: " + id);
        }

        return "redirect:/product";
    }

    @GetMapping("/product/delete/confirmation/{id}")
    public String confirmDeleteProduct(@PathVariable Long id) {
        System.out.println("Confirming deletion of product with ID: " + id);
        productService.deleteById(id);
        return "redirect:/product";
    }

}
