package com.example.fashion_spring_boot.dto;

import com.example.fashion_spring_boot.entity.Product;

public class CartItem {
    private Product product;
    private int quantity;

    public double getTotalPrice() {
        return product.getProductPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
