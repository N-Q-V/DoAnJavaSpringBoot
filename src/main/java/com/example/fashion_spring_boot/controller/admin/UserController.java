package com.example.fashion_spring_boot.controller.admin;

import com.example.fashion_spring_boot.dao.UserRepository;
import com.example.fashion_spring_boot.entity.User;
import com.example.fashion_spring_boot.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {

    final UserService userService;
    final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String user(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<User> users = userService.findALlUser(page, size);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "admin/users/user";
    }

    @PostMapping("/update-enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateEnabled(@RequestParam Long id, @RequestParam Boolean enabled) {
        try {
            // Tìm người dùng theo id
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            user.setEnabled(enabled);
            userRepository.save(user);
            System.out.println("--------check----------");
            return ResponseEntity.ok("User enabled status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating user enabled status: " + e.getMessage());
        }
    }
}
