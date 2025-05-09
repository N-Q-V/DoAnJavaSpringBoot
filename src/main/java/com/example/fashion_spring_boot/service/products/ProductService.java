package com.example.fashion_spring_boot.service.products;

import org.springframework.ui.Model;
import com.example.fashion_spring_boot.dto.ProductDto;
import com.example.fashion_spring_boot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findProductByIdAndLanguage(Long id, String language);

//    List<Product> findByLanguage(String language);

    void save(Product product);

    ProductDto productConvertToProductDto(Product product);

    Product productDtoConvertToProduct(ProductDto dto);

    Product productDtoEnConvertToProduct(ProductDto dto);

    Product productDtoViConvertToProduct(ProductDto dto);

    String saveFile(MultipartFile file);

    String editFile(MultipartFile file, String oldImage);

    Long findMaxProductId();

    void deleteProductById(Long id);

    String copyFile(String existingPath);

    Page<Product> getAllProducts(String language, int page, int size);

    Page<Product> findProductByProductNameAndLanguage(String productName, String language, int page, int size);

    Page<Product> findProductByCategory(Integer categoryId, String language, int page, int size);

    List<Product> getAllProductByLanguage(String language);
}
