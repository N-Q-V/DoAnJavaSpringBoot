package com.example.fashion_spring_boot.dao;
import com.example.fashion_spring_boot.dto.CategoryDto;
import com.example.fashion_spring_boot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
