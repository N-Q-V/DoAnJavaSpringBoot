package com.example.fashion_spring_boot.controller.admin;

import com.example.fashion_spring_boot.entity.Order;
import com.example.fashion_spring_boot.entity.Status;
import com.example.fashion_spring_boot.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class OrderController {
    final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String order(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "8") int size,
                        Model model) {
        Page<Order> orders = orderService.getAllOrder(page, size);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", Status.values());
        return "admin/orders/order";
    }

    @GetMapping("/orderDetail")
    public String orderDetail(@RequestParam("id") long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "admin/orders/orderDetail";
    }

    @PostMapping("/order/update-status")
    @ResponseBody
    public ResponseEntity<String> updateStatus(
            @RequestParam("id") long id,
            @RequestParam("status") Status status) {
        Order order = orderService.getOrderById(id);
        order.setStatus(Status.valueOf(status.name()));
        orderService.saveOrder(order);
        return ResponseEntity.ok("Order status updated successfully");
    }
}
