package com.example.fashion_spring_boot.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class LoginController {
    @GetMapping("/showLoginPage")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/showPage403")
    public String showPage403() {
        return "error/403";
    }
}
