package com.example.fashion_spring_boot.service.User;

import com.example.fashion_spring_boot.dao.UserRepository;
import com.example.fashion_spring_boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
//        System.out.println("First name: " + user.getFirstName());
        return new CustomUserDetails(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findALlUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllByOrderByIdDesc(pageable);
    }
}
