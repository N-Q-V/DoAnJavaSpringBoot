package com.example.fashion_spring_boot.controller.admin;

import com.example.fashion_spring_boot.dto.ProductDto;
import com.example.fashion_spring_boot.entity.*;
import com.example.fashion_spring_boot.service.categories.CategoryService;
import com.example.fashion_spring_boot.service.localization.GetCurrentLocale;
import com.example.fashion_spring_boot.service.products.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    final ProductService productService;
    final CategoryService categoryService;
    final GetCurrentLocale getCurrentLocale;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, GetCurrentLocale getCurrentLocale) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.getCurrentLocale = getCurrentLocale;
    }

    @GetMapping("")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(value = "lang", required = false) String lang, Model model) {
        // Lấy ngôn ngữ hiện tại nếu không có lang trên URL
        String language = getCurrentLocale.currentLocale(lang);
        // Gọi service để lấy danh sách sản phẩm theo ngôn ngữ
        Page<Product> products = productService.getAllProducts(language, page, size);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("products", products);
        return "admin/products/product";
    }

    @GetMapping("/form")
    public String form(Model model) {
        ProductDto productDto = new ProductDto();
        productDto.setLanguageEn("en");
        productDto.setLanguageVi("vi");
        productDto.setId(productService.findMaxProductId() + 1);
        model.addAttribute("productDto", productDto);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("sizes", Size.values());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/products/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("productDto", productDto);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("sizes", Size.values());
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            return "admin/products/product-form";
        }
        // Lưu file gốc 1 lần
        String imagePathEn = productService.saveFile(productDto.getProductImage());
        String thumbPathEn = productService.saveFile(productDto.getProductThumbnail());
        // Tạo bản sao với tên khác
        String imagePathVi = productService.copyFile(imagePathEn);
        String thumbPathVi = productService.copyFile(thumbPathEn);
        //Set dto imagePath En - Vi
        productDto.setImagePathEn(imagePathEn);
        productDto.setImagePathVi(imagePathVi);
        //Set dto thumbPath En - Vi
        productDto.setThumbPathEn(thumbPathEn);
        productDto.setThumbPathVi(thumbPathVi);
        var productEn = productService.productDtoEnConvertToProduct(productDto);
        var productVi = productService.productDtoViConvertToProduct(productDto);
        productService.save(productEn);
        productService.save(productVi);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id,
                       @RequestParam("language") String language,
                       Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("sizes", Size.values());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        var product = productService.findProductByIdAndLanguage(id, language);
        //select sizes -> as list
        model.addAttribute("selectedSizes", Arrays.asList(product.getSize().split(",")));
        model.addAttribute("productDto", productService.productConvertToProductDto(product));
        return "admin/products/product-form-edit";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("productDto") ProductDto productDto,
                              @RequestParam("oldImage") String oldImage,
                              @RequestParam("oldThumb") String oldThumb) {
        //productImage
        String imagePath = productService.editFile(productDto.getProductImage(), oldImage);
        //productThumb
        String thumbPath = productService.editFile(productDto.getProductThumbnail(), oldThumb);
        productDto.setImagePath(imagePath);
        productDto.setThumbPath(thumbPath);
        var product = productService.productDtoConvertToProduct(productDto);
        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/searchProductName")
    public String searchProductName(@RequestParam("productName") String productName,
                                    @RequestParam(value = "lang", required = false) String lang,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    Model model) {
        String language = getCurrentLocale.currentLocale(lang);
        Page<Product> products = productService.findProductByProductNameAndLanguage(productName, language, page, size);
        model.addAttribute("products", products);
        model.addAttribute("productName", productName);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        System.out.println(language);
        return "admin/products/product";
    }
}
