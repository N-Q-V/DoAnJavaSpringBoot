package com.example.fashion_spring_boot.dao;

import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByProductId_Language(String language);

    @Query("SELECT MAX(p.productId.id) FROM Product p")
    Long findMaxProductId();

    @Query("SELECT s From Product s WHERE s.productId.id = :id and s.productId.language = :language")
    Product findProductByIdAndLanguage(@Param("id") Long id, @Param("language") String language);

    List<Product> findByProductId_id(Long id);

    Page<Product> findByProductId_Language(String language, Pageable pageable);

    @Query("SELECT s FROM Product s WHERE s.productName LIKE %:productName% AND s.productId.language = :language")
    Page<Product> findProductByProductNameAndLanguage(@Param("productName") String productName,@Param("language") String language, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.productId.language = :language")
    Page<Product> findProductByCategoryId(Integer categoryId, String language, Pageable pageable);

    List<Product> findProductByProductId_Language(String language);
}
