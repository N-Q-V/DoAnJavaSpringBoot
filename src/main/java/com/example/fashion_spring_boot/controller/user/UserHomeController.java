package com.example.fashion_spring_boot.controller.user;

import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.entity.Product;
import com.example.fashion_spring_boot.service.categories.CategoryService;
import com.example.fashion_spring_boot.service.localization.GetCurrentLocale;
import com.example.fashion_spring_boot.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("")
public class UserHomeController {

    final GetCurrentLocale getCurrentLocale;
    final CategoryService categoryService;
    final ProductService productService;

    @Autowired
    public UserHomeController(GetCurrentLocale getCurrentLocale, CategoryService categoryService, ProductService productService) {
        this.getCurrentLocale = getCurrentLocale;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping()
    public String home(@RequestParam(value = "lang", required = false) String lang, Model model) {
        String language = getCurrentLocale.currentLocale(lang);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        //Lấy product theo page
        List<Product> products = productService.getAllProductByLanguage(language);
        model.addAttribute("products", products);
        return "user/products/home";
    }
}
