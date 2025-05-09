package com.example.fashion_spring_boot.controller.auth;

import com.example.fashion_spring_boot.dao.RoleRepository;
import com.example.fashion_spring_boot.dao.UserRepository;
import com.example.fashion_spring_boot.dto.RegisterUser;
import com.example.fashion_spring_boot.entity.Role;
import com.example.fashion_spring_boot.entity.User;
import com.example.fashion_spring_boot.service.User.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/register")
public class RegisterController {
    final UserRepository userRepository;
    final UserService userService;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final RoleRepository roleRepository;

    @Autowired
    public RegisterController(UserRepository userRepository, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/showRegisterForm")
    public String showRegisterForm(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "/auth/register/form";
    }

    //Loai bỏ khoảng trăng để validate đúng
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/process")
    public String process(@Valid @ModelAttribute("registerUser") RegisterUser registerUser, BindingResult bindingResult, Model model, HttpSession session) throws SQLException, IOException {
        String username = registerUser.getUsername();
        //form validate
        if (bindingResult.hasErrors()) {
            return "/auth/register/form";
        }
        //check username
        User userExisting = userRepository.findByUsername(username);
        if (userExisting != null) {
            model.addAttribute("registerUser", new RegisterUser());
            model.addAttribute("my_error", "Account already exists");
            return "/auth/register/form";
        }

        if (userRepository.existsByEmail(registerUser.getEmail())) {
            model.addAttribute("my_error", "Email already exists");
            return "/auth/register/form";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setEmail(registerUser.getEmail());
        user.setEnabled(true);
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);
        userService.save(user);

        //Thông báo thành công
        session.setAttribute("myuser", user);
        return "/auth/register/confirmation";
    }
}
