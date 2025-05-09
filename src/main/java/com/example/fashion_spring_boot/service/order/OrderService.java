package com.example.fashion_spring_boot.service.order;

import com.example.fashion_spring_boot.dto.CheckoutForm;
import com.example.fashion_spring_boot.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    void add(Long id, String lang, int quantity);

    void update(Long id, int newQuantity);

    void delete(String lang, Long id);

    void viewCart(Model model);

    void getCheckOut(Principal principal);

    void postCheckOut(CheckoutForm checkoutForm, BindingResult result, Principal principal, Model model);

    List<Order> historyOrders(Principal principal);

    Page<Order> getAllOrder(int page, int size);

    Order getOrderById(Long id);

    void saveOrder(Order order);
}
