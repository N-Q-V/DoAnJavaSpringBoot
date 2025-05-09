package com.example.fashion_spring_boot.controller.user;

import com.example.fashion_spring_boot.dto.UserDto;
import org.springframework.ui.Model;
import com.example.fashion_spring_boot.entity.User;
import com.example.fashion_spring_boot.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class AccountController {
    final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String account(Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
            return "auth/register/account";
        } else {
            return "auth/login";
        }
    }

    @PostMapping("/account")
    public String account(@ModelAttribute("userDto") UserDto userDto, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setAddress(userDto.getAddress());
            user.setPhoneNumber(userDto.getPhoneNumber());
            userService.save(user);
            redirectAttributes.addFlashAttribute("msg", "Information is updated!");
        }
        return "redirect:/home/account";
    }
}
