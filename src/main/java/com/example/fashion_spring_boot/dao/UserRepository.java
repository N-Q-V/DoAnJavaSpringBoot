package com.example.fashion_spring_boot.dao;

import com.example.fashion_spring_boot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Page<User> findAllByOrderByIdDesc(Pageable pageable);
}
