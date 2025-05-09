package com.example.fashion_spring_boot.dto;

import com.example.fashion_spring_boot.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@SessionScope
public class Cart {
    private Map<Long, CartItem> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        CartItem item = items.get(product.getProductId().getId()); // tìm xem sản phẩm có trong cart chưa
        if (item == null) {
            item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            items.put(product.getProductId().getId(), item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
    }

    public void removeProduct(Product product) {
        items.remove(product.getProductId().getId());
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void updateProductQuantity(Product product, int newQuantity) {
        CartItem item = items.get(product.getProductId().getId());
        if (item != null) {
            if (newQuantity > 0) {
                item.setQuantity(newQuantity);
            } else {
                items.remove(product.getProductId().getId());
            }
        }
    }

    public void clear() {
        items.clear();
    }
}
