package com.ecommerce.model;

public class CartItemView {
        private final Product product;
        private int quantity;

        public CartItemView(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public void incrementQuantity() {
            this.quantity++;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public Double getSubtotal() {
            return product.getPrice() * quantity;
        }
    }