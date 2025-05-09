package com.example.fashion_spring_boot.controller.admin;

import com.example.fashion_spring_boot.dto.CategoryDto;
import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.service.categories.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String categorySubmit(@Valid @ModelAttribute("category") CategoryDto categoryDto,
                                 BindingResult result,
                                 @RequestParam("oldImage") String oldImage,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/categories/category-form";
        }
        String path = categoryService.saveFile(categoryDto.getCategoryImage(), oldImage);
        categoryDto.setImagePath(path);
        var category = categoryService.convertCategoryDtoToCategory(categoryDto);
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("msg_access", "Successfully save category!");
        return "redirect:/admin/category";
    }

    @GetMapping("/edit")
    public String categoryEditForm(@RequestParam("id") int id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/categories/category-form";
    }

    @GetMapping("/delete")
    public String categoryDelete(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        categoryService.deleteFile(id);
        redirectAttributes.addFlashAttribute("msg_deleted", "Category deleted!");
        return "redirect:/admin/category";
    }

}
