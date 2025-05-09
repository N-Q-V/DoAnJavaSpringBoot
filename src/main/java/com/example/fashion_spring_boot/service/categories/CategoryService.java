package com.example.fashion_spring_boot.service.categories;

import com.example.fashion_spring_boot.dto.CategoryDto;
import com.example.fashion_spring_boot.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(int id);
    void save(Category category);
    void delete(int id);
    Category convertCategoryDtoToCategory(CategoryDto categoryDto);
    String saveFile(MultipartFile file, String oldImage);
    void deleteFile(int id);
}
