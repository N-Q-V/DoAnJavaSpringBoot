package com.example.fashion_spring_boot.service.User;

import com.example.fashion_spring_boot.dao.RoleRepository;
import com.example.fashion_spring_boot.dto.RegisterUser;
import com.example.fashion_spring_boot.entity.Role;
import com.example.fashion_spring_boot.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RegisterServiceImpl implements RegisterService {
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final RoleRepository roleRepository;
    final UserService userService;

    @Autowired
    public RegisterServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void process(@Valid @ModelAttribute("registerUser") RegisterUser registerUser, BindingResult bindingResult, Model model, HttpSession session) {
        String username = registerUser.getUsername();
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setEmail(registerUser.getEmail());
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);
        userService.save(user);
    }
}
