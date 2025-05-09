package com.example.fashion_spring_boot.dto;

import com.example.fashion_spring_boot.entity.Gender;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductDto {
    private Long id;
    private String language;

    private String languageEn;
    private String languageVi;

    @NotBlank(message = "Product code cannot be blank")
    private String productCode;

    private String productName;

    @NotBlank(message = "Product name cannot be blank")
    private String productNameEn;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productNameVi;

    private MultipartFile productImage;

    private String imagePath;
    private String imagePathEn;
    private String imagePathVi;

    private MultipartFile productThumbnail;

    private String thumbPath;
    private String thumbPathEn;
    private String thumbPathVi;

    private Double productPrice;

    @NotNull(message = "Product name cannot be blank")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double productPriceEn;
    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double productPriceVi;

    private String productDescription;

    @NotBlank(message = "Description cannot be blank")
    private String productDescriptionEn;
    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    private String productDescriptionVi;

    private Gender gender;

    @NotEmpty(message = "Please select at least one size")
    private List<String> size;

    private int category;

    public ProductDto() {
    }

    public ProductDto(Long id, String language, String languageEn, String languageVi, String productCode, String productName, String productNameEn, String productNameVi, MultipartFile productImage, String imagePath, String imagePathEn, String imagePathVi, MultipartFile productThumbnail, String thumbPath, String thumbPathEn, String thumbPathVi, Double productPrice, Double productPriceEn, Double productPriceVi, String productDescription, String productDescriptionEn, String productDescriptionVi, Gender gender, List<String> size, int category) {
        this.id = id;
        this.language = language;
        this.languageEn = languageEn;
        this.languageVi = languageVi;
        this.productCode = productCode;
        this.productName = productName;
        this.productNameEn = productNameEn;
        this.productNameVi = productNameVi;
        this.productImage = productImage;
        this.imagePath = imagePath;
        this.imagePathEn = imagePathEn;
        this.imagePathVi = imagePathVi;
        this.productThumbnail = productThumbnail;
        this.thumbPath = thumbPath;
        this.thumbPathEn = thumbPathEn;
        this.thumbPathVi = thumbPathVi;
        this.productPrice = productPrice;
        this.productPriceEn = productPriceEn;
        this.productPriceVi = productPriceVi;
        this.productDescription = productDescription;
        this.productDescriptionEn = productDescriptionEn;
        this.productDescriptionVi = productDescriptionVi;
        this.gender = gender;
        this.size = size;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageEn() {
        return languageEn;
    }

    public void setLanguageEn(String languageEn) {
        this.languageEn = languageEn;
    }

    public String getLanguageVi() {
        return languageVi;
    }

    public void setLanguageVi(String languageVi) {
        this.languageVi = languageVi;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    public String getProductNameVi() {
        return productNameVi;
    }

    public void setProductNameVi(String productNameVi) {
        this.productNameVi = productNameVi;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePathEn() {
        return imagePathEn;
    }

    public void setImagePathEn(String imagePathEn) {
        this.imagePathEn = imagePathEn;
    }

    public String getImagePathVi() {
        return imagePathVi;
    }

    public void setImagePathVi(String imagePathVi) {
        this.imagePathVi = imagePathVi;
    }

    public MultipartFile getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(MultipartFile productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getThumbPathEn() {
        return thumbPathEn;
    }

    public void setThumbPathEn(String thumbPathEn) {
        this.thumbPathEn = thumbPathEn;
    }

    public String getThumbPathVi() {
        return thumbPathVi;
    }

    public void setThumbPathVi(String thumbPathVi) {
        this.thumbPathVi = thumbPathVi;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductPriceEn() {
        return productPriceEn;
    }

    public void setProductPriceEn(Double productPriceEn) {
        this.productPriceEn = productPriceEn;
    }

    public Double getProductPriceVi() {
        return productPriceVi;
    }

    public void setProductPriceVi(Double productPriceVi) {
        this.productPriceVi = productPriceVi;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescriptionEn() {
        return productDescriptionEn;
    }

    public void setProductDescriptionEn(String productDescriptionEn) {
        this.productDescriptionEn = productDescriptionEn;
    }

    public String getProductDescriptionVi() {
        return productDescriptionVi;
    }

    public void setProductDescriptionVi(String productDescriptionVi) {
        this.productDescriptionVi = productDescriptionVi;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
