package com.example.fashion_spring_boot.dao;

import com.example.fashion_spring_boot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
