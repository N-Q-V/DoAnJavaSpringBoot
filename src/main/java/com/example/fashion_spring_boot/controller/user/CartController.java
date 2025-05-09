package com.example.fashion_spring_boot.controller.user;

import com.example.fashion_spring_boot.dao.UserRepository;
import com.example.fashion_spring_boot.dto.Cart;
import com.example.fashion_spring_boot.dto.CheckoutForm;
import com.example.fashion_spring_boot.entity.Order;
import com.example.fashion_spring_boot.entity.User;
import com.example.fashion_spring_boot.exception.CartException;
import com.example.fashion_spring_boot.service.User.CustomUserDetails;
import com.example.fashion_spring_boot.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home/cart")
public class CartController {
    final OrderService orderService;
    final Cart cart;
    final UserRepository userRepository;

    @Autowired
    public CartController(OrderService orderService, Cart cart, UserRepository userRepository) {
        this.orderService = orderService;
        this.cart = cart;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addCart(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam Long id,
            @RequestParam int quantity) {
        orderService.add(id, lang, quantity);
        return "redirect:/home/cart/view";
    }


    @GetMapping("/view")
    public String viewCart(Model model) {
        orderService.viewCart(model);
        return "user/cart/view";
    }

    @PostMapping("/remove")
    public String removeCart(
            @RequestParam(value = "lang", required = false) String lang,
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {
        orderService.delete(lang, id);
        redirectAttributes.addFlashAttribute("successMessage", "Successfully deleted cart.");
        return "redirect:/home/cart/view";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        CheckoutForm checkoutForm = new CheckoutForm();
        if (principal != null) {
            Authentication auth = (Authentication) principal;
            CustomUserDetails userSession = (CustomUserDetails) auth.getPrincipal();
            User user = userRepository.findByUsername(userSession.getUsername());
            checkoutForm.setFirstName(user.getFirstName());
            checkoutForm.setLastName(user.getLastName());
            checkoutForm.setEmail(user.getEmail());
            checkoutForm.setPhoneNumber(user.getPhoneNumber());
            checkoutForm.setAddress(user.getAddress());
            model.addAttribute("user", user);
        }
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        model.addAttribute("checkoutForm", checkoutForm);
        return "user/cart/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(
            @Valid @ModelAttribute("checkoutForm") CheckoutForm checkoutForm,
            BindingResult result,
            Principal principal,
            Model model) {
        if (result.hasErrors()) {
            // Nếu có lỗi, trả về lại view để hiển thị lỗi
            orderService.viewCart(model);
            return "/user/cart/checkout";
        }
        try {
            orderService.postCheckOut(checkoutForm, result, principal, model);
        } catch (CartException e) {
            // Thêm thông báo lỗi vào model để hiển thị trên view
            model.addAttribute("errorMessage", e.getMessage());
            return "user/cart/checkout";
        }
        return "user/cart/confirmation";
    }

    @GetMapping("/my-order")
    public String myOrder(Model model, Principal principal) {
        if (principal != null) {
            List<Order> orders = orderService.historyOrders(principal);
            model.addAttribute("orders", orders);
            return "user/cart/my-order";
        }
        return "auth/login";
    }

    @PostMapping("/update")
    public String updateCart(
            @RequestParam("id") Long id,
            @RequestParam("quantity") int quantity
    ) {
        orderService.update(id, quantity);
        return "redirect:/home/cart/view";
    }
}
