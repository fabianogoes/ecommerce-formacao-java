package com.ecommerce.service;

import com.ecommerce.model.CartItemView;
import com.ecommerce.model.Product;
import com.ecommerce.repositoty.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductRepository productRepository;

    public boolean existProductById(Long productId) {
        return productRepository.existsById(productId);
    }

    public List<Product> createShoppingCart(Long productId, HttpSession session) {
        System.out.println("Retrieving shopping cart items");
        Product product = productRepository.findById(productId).get();


        List<Product> shoppingCart;
        if (session.getAttribute("shoppingCart") != null) {
            shoppingCart = (List<Product>) session.getAttribute("shoppingCart");
        } else {
            shoppingCart = new ArrayList<>();
        }

        System.out.println("Product added to cart: " + product.getName());
        System.out.println("Total items in cart: " + shoppingCart.size());

        shoppingCart.add(product);
        return shoppingCart;
    }

    public ArrayList<CartItemView> shoppingCartView(HttpSession session) {
        List<Product> shoppingCart = (List<Product>) session.getAttribute("shoppingCart");

        Map<Long, CartItemView> cartItemsMap = new HashMap<>();
        for (Product product : shoppingCart) {
            if (cartItemsMap.containsKey(product.getId())) {
                CartItemView item = cartItemsMap.get(product.getId());
                item.incrementQuantity();
            } else {
                cartItemsMap.put(product.getId(), new CartItemView(product, 1));
            }
        }

        return new ArrayList<>(cartItemsMap.values());
    }

    public double getSubtotalByCart(ArrayList<CartItemView> cart) {
        System.out.println("Calculating subtotal for cart with " + cart.size() + " items.");
        return cart.stream()
                .map(CartItemView::getSubtotal)
                .reduce(0.0, Double::sum);
    }

}
