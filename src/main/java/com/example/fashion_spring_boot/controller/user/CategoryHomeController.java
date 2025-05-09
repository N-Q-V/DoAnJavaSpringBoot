package com.example.fashion_spring_boot.controller.user;

import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.entity.Product;
import com.example.fashion_spring_boot.service.categories.CategoryService;
import com.example.fashion_spring_boot.service.localization.GetCurrentLocale;
import com.example.fashion_spring_boot.service.products.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/home")
public class CategoryHomeController {
    final CategoryService categoryService;
    final ProductService productService;
    final GetCurrentLocale getCurrentLocale;

    @Autowired
    public CategoryHomeController(CategoryService categoryService, ProductService productService, GetCurrentLocale getCurrentLocale) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.getCurrentLocale = getCurrentLocale;
    }

    @GetMapping("/category")
    public String category(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(value = "lang", required = false) String lang, Model model) {

        // Lấy ngôn ngữ hiện tại nếu không có lang trên URL
        String language = getCurrentLocale.currentLocale(lang);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        //Lấy product theo page
        Page<Product> products;
        if (categoryId != null) {
            products = productService.findProductByCategory(categoryId, language, page, size);
        } else {
            products = productService.getAllProducts(language, page, size);
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("products", products);
        return "user/products/category";
    }

    @GetMapping("/product-detail")
    public String productDetail(@RequestParam("id") Long id, @RequestParam("language") String language, Model model) {
        Product product = productService.findProductByIdAndLanguage(id, language);
        model.addAttribute("product", product);
        return "user/products/productDetail";
    }
}
