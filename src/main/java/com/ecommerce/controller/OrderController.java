package com.ecommerce.controller;

import com.ecommerce.model.CartItemView;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public String orderAdmin(
            Model model,
            @RequestParam(required = false) Order.OrderStatus status,
            HttpSession session
    ) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        } else {
            System.out.println("User is logged in as: " + session.getAttribute("currentUser"));
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
        }

        model.addAttribute("adminSection", "order");

        User currentUser = (User) session.getAttribute("currentUser");
        boolean isAdmin = "admin@admin.com".equalsIgnoreCase(currentUser.getEmail());

        // Buscar pedidos com ou sem filtro de status
        Iterable<Order> orders;

        if (isAdmin) {
            // Admin vê todos os pedidos de todos os usuários
            if (status != null) {
                orders = orderService.findAllByStatus(status);
                model.addAttribute("statusFilter", status);
            } else {
                orders = orderService.findAll();
                model.addAttribute("statusFilter", "ALL");
            }
        } else {
            // Usuários comuns veem apenas seus próprios pedidos
            if (status != null) {
                orders = orderService.findAllByStatusAndUserId(status, currentUser.getId());
                model.addAttribute("statusFilter", status);
            } else {
                orders = orderService.findAllByUserId(currentUser.getId());
                model.addAttribute("statusFilter", "ALL");
            }
        }

        model.addAttribute("orders", orders);
        model.addAttribute("statusList", Order.OrderStatus.values());
        model.addAttribute("isAdmin", isAdmin);

        return "admin";
    }

    @GetMapping("/order/checkout")
    public String checkout(HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            System.out.println("User not logged in. Redirecting to SignIn.");
            return "redirect:/signin";
        }

        List<CartItemView> shoppingCartView =
                (List<CartItemView>) session.getAttribute("shoppingCartView");

        if (shoppingCartView == null || shoppingCartView.isEmpty()) {
            System.out.println("Cart is empty. Redirecting to home.");
            return "redirect:/";
        }

        session.setAttribute("checkoutView", shoppingCartView);
        return "redirect:/order/checkout-page";
    }

    @GetMapping("/order/checkout-page")
    public String checkoutPage(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/signin";
        }

        User currentUser = (User) session.getAttribute("currentUser");
        Integer cartTotalItems = (Integer) session.getAttribute("cartTotalItems");
        Double cartTotalValue = (Double) session.getAttribute("cartTotalValue");

        List<CartItemView> checkoutView =
                (List<CartItemView>) session.getAttribute("checkoutView");

        if (checkoutView == null || checkoutView.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("checkoutView", checkoutView);
        model.addAttribute("cartTotalItems", cartTotalItems);
        model.addAttribute("cartTotalValue", cartTotalValue);
        model.addAttribute("currentUser", currentUser);
        return "checkout";
    }

    @PostMapping("/order/finalize")
    public String finalizeOrder(HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/signin";
        }

        User currentUser = (User) session.getAttribute("currentUser");

        List<CartItemView> checkoutView =
                (List<CartItemView>) session.getAttribute("checkoutView");

        if (checkoutView == null || checkoutView.isEmpty()) {
            return "redirect:/";
        }

        Integer cartTotalItems = (Integer) session.getAttribute("cartTotalItems");
        Double cartTotalValue = (Double) session.getAttribute("cartTotalValue");

        Order order = new Order(currentUser, cartTotalValue, cartTotalItems);
        for (CartItemView itemView : checkoutView) {
            OrderItem item = new OrderItem(
                    itemView.getProduct(),
                    itemView.getProduct().getPrice(),
                    itemView.getQuantity(),
                    itemView.getSubtotal()
            );
            order.addItem(item);
        }

        orderService.save(order);

        session.setAttribute("orderSuccess", true);
        session.setAttribute("orderId", order.getId());
        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String orderSuccess(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/signin";
        }

        User currentUser = (User) session.getAttribute("currentUser");
        Boolean orderSuccess = (Boolean) session.getAttribute("orderSuccess");
        Long orderId = (Long) session.getAttribute("orderId");

        if (orderSuccess == null || !orderSuccess) {
            return "redirect:/";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("orderId", orderId);

        session.removeAttribute("orderSuccess");
        session.removeAttribute("orderId");
        session.removeAttribute("shoppingCart");
        session.removeAttribute("shoppingCartView");

        return "order-success";
    }

}
