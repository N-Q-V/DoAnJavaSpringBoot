package com.example.fashion_spring_boot.controller.admin;

import com.example.fashion_spring_boot.dto.CategoryDto;
import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.service.categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String category(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories/category";
    }

    @GetMapping("/form")
    public String categoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/category-form";
    }

    @PostMapping("/save")
    public String categorySubmit(@ModelAttribute("category") CategoryDto categoryDto, @RequestParam("oldImage") String oldImage, Model model) {
        String path = categoryService.saveFile(categoryDto.getCategoryImage(), oldImage);
        categoryDto.setImagePath(path);
        var category = categoryService.convertCategoryDtoToCategory(categoryDto);
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit")
    public String categoryEditForm(@RequestParam("id") int id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/categories/category-form";
    }

    @GetMapping("/delete")
    public String categoryDelete(@RequestParam("id") int id) {
        categoryService.deleteFile(id);
        return "redirect:/admin/category";
    }

}
