package com.example.fashion_spring_boot.service.order;

import com.example.fashion_spring_boot.dao.OrderDetailRepository;
import com.example.fashion_spring_boot.dao.OrderRepository;
import com.example.fashion_spring_boot.dto.Cart;
import com.example.fashion_spring_boot.dto.CartItem;
import com.example.fashion_spring_boot.dto.CheckoutForm;
import com.example.fashion_spring_boot.entity.*;
import com.example.fashion_spring_boot.exception.CartException;
import com.example.fashion_spring_boot.service.User.CustomUserDetails;
import com.example.fashion_spring_boot.service.User.UserService;
import com.example.fashion_spring_boot.service.localization.GetCurrentLocale;
import com.example.fashion_spring_boot.service.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    final ProductService productService;
    final Cart cart;
    final UserService userService;
    final OrderRepository orderRepository;
    final GetCurrentLocale getCurrentLocale;
    final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderServiceImpl(ProductService productService, Cart cart, UserService userService, OrderRepository orderRepository, GetCurrentLocale getCurrentLocale, OrderDetailRepository orderDetailRepository) {
        this.productService = productService;
        this.cart = cart;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.getCurrentLocale = getCurrentLocale;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void add(Long id, String lang, int quantity) {
        String language = getCurrentLocale.currentLocale(lang);
        Product product = productService.findProductByIdAndLanguage(id, language);
        if (product != null) {
            cart.addProduct(product, quantity);
        }
    }

    @Override
    public void update(Long id, int newQuantity) {
        Product product = productService.findProductByIdAndLanguage(id, LocaleContextHolder.getLocale().getLanguage());
        cart.updateProductQuantity(product, newQuantity);
    }

    @Override
    public void delete(String lang, Long id) {
        String language = getCurrentLocale.currentLocale(lang);
        Product product = productService.findProductByIdAndLanguage(id, language);
        cart.removeProduct(product);
    }

    @Override
    public void viewCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
    }

    @Override
    public void getCheckOut(Principal principal) {

    }

    @Override
    public void postCheckOut(@Valid @ModelAttribute("checkoutForm") CheckoutForm checkoutForm, BindingResult result, Principal principal, Model model) {
        long user_id = 2L; // Guest
        if (principal != null) {
            Authentication auth = (Authentication) principal;
            CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
            user_id = user.getId();
        }

        // Tìm người dùng
        User user = userService.findById(user_id);
        if (user == null) {
            throw new CartException("User not found.");
        }

        // Kiểm tra nếu giỏ hàng trống
        if (cart.getItems().isEmpty()) {
            throw new CartException("Cannot checkout because cart is empty.");
        }

        // Tạo đơn hàng
        Order order = new Order();
        order.setUser(user);
        String customerName = checkoutForm.getFirstName() + " " + checkoutForm.getLastName();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomerName(customerName);
        order.setAddress(checkoutForm.getAddress());
        order.setPhone(checkoutForm.getPhoneNumber());
        order.setEmail(checkoutForm.getEmail());
        order.setStatus(Status.Processing);
        order.setTotalPrice(cart.getTotalPrice());

        // Tạo chi tiết đơn hàng
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(cartItem.getProduct());
            detail.setQuantity(cartItem.getQuantity());
            detail.setPrice(cartItem.getTotalPrice());
            orderDetails.add(detail);
        }
        order.setOrderDetails(orderDetails);

        // Lưu đơn hàng vào cơ sở dữ liệu và xóa giỏ hàng
        try {
            orderRepository.save(order);
            cart.clear();
        } catch (Exception e) {
            throw new CartException("Unable to save order.");
        }
    }


    @Override
    public List<Order> historyOrders(Principal principal) {
        Authentication auth = (Authentication) principal;
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        long user_id = user.getId();
        return orderRepository.findOrderByUser_Id(user_id);
    }

    @Override
    public Page<Order> getAllOrder(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAllByOrderByOrderDateDesc(pageable);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findOrderById(id);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
