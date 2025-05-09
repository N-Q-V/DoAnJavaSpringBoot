package com.example.fashion_spring_boot.service.User;

import com.example.fashion_spring_boot.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
     User save(User user);

     User findById(long id);

     User findByUsername(String username);
}
