package com.example.fashion_spring_boot.service.User;

import com.example.fashion_spring_boot.dto.RegisterUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface RegisterService {
    void process(RegisterUser registerUser, BindingResult bindingResult, Model model, HttpSession session);
}
