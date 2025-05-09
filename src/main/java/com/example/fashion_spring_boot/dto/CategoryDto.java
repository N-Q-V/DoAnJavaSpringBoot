package com.example.fashion_spring_boot.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CategoryDto {
    private int id;
    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
    private MultipartFile categoryImage;
    private String imagePath;

    public CategoryDto() {
    }

    public CategoryDto(String categoryName, MultipartFile categoryImage, String imagePath) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MultipartFile getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(MultipartFile categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
