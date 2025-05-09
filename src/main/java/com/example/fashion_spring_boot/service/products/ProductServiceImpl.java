package com.example.fashion_spring_boot.service.products;

import org.springframework.ui.Model;
import com.example.fashion_spring_boot.dao.ProductRepository;
import com.example.fashion_spring_boot.dto.ProductDto;
import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.entity.Product;
import com.example.fashion_spring_boot.entity.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductByIdAndLanguage(Long id, String language) {
        return productRepository.findProductByIdAndLanguage(id, language);
    }

    @Override
    public void save(Product product) {
        productRepository.saveAndFlush(product);
    }

    //Convert product to productDTO
    @Override
    public ProductDto productConvertToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getProductId().getId());
        dto.setLanguage(product.getProductId().getLanguage());

        // Set các thuộc tính còn lại
        dto.setProductName(product.getProductName());
        dto.setProductCode(product.getProductCode());
        dto.setProductDescription(product.getProductDescription());
        dto.setImagePath(product.getProductImage());
        dto.setThumbPath(product.getProductThumbnail());
        dto.setProductPrice(product.getProductPrice());
        dto.setGender(product.getGender());
        //set list size
        dto.setSize(Arrays.asList(product.getSize().split(",")));

        // Set category (chỉ lấy ID của Category)
        dto.setCategory(product.getCategory().getId());

        return dto;
    }

    //Convert productDTO to product
    @Override
    public Product productDtoConvertToProduct(ProductDto dto) {
        Product product = new Product();

        // Tạo khóa chính phức hợp
        ProductId productId = new ProductId();
        productId.setId(dto.getId());
        productId.setLanguage(dto.getLanguage());
        product.setProductId(productId);

        // Set các thuộc tính còn lại
        product.setProductName(dto.getProductName());
        product.setProductCode(dto.getProductCode());
        product.setProductDescription(dto.getProductDescription());
        product.setProductImage(dto.getImagePath());
        product.setProductThumbnail(dto.getThumbPath());
        product.setProductPrice(dto.getProductPrice());
        product.setGender(dto.getGender());
        //set list size
        product.setSize(String.join(",", dto.getSize()));

        // Set category
        Category category = new Category();
        category.setId(dto.getCategory());
        product.setCategory(category);

        return product;
    }


    //Convert productDTO (EN) to product
    @Override
    public Product productDtoEnConvertToProduct(ProductDto dto) {
        Product product = new Product();

        // Tạo khóa chính phức hợp
        ProductId productId = new ProductId();
        productId.setId(dto.getId());
        productId.setLanguage(dto.getLanguageEn());
        product.setProductId(productId);

        // Set các thuộc tính còn lại
        product.setProductName(dto.getProductNameEn());
        product.setProductCode(dto.getProductCode());
        product.setProductDescription(dto.getProductDescriptionEn());
        product.setProductImage(dto.getImagePathEn());
        product.setProductThumbnail(dto.getThumbPathEn());
        product.setProductPrice(dto.getProductPriceEn());
        product.setGender(dto.getGender());
        //set string size
        product.setSize(String.join(",", dto.getSize()));

        // Set category
        Category category = new Category();
        category.setId(dto.getCategory());
        product.setCategory(category);

        return product;
    }

    //Convert productDTO (VI) to product
    @Override
    public Product productDtoViConvertToProduct(ProductDto dto) {
        Product product = new Product();

        // Tạo khóa chính phức hợp
        ProductId productId = new ProductId();
        productId.setId(dto.getId());
        productId.setLanguage(dto.getLanguageVi());
        product.setProductId(productId);

        // Set các thuộc tính còn lại
        product.setProductName(dto.getProductNameVi());
        product.setProductCode(dto.getProductCode());
        product.setProductDescription(dto.getProductDescriptionVi());
        product.setProductImage(dto.getImagePathVi());
        product.setProductThumbnail(dto.getThumbPathVi());
        product.setProductPrice(dto.getProductPriceVi());
        product.setGender(dto.getGender());
        //set string size
        product.setSize(String.join(",", dto.getSize()));

        // Set category
        Category category = new Category();
        category.setId(dto.getCategory());
        product.setCategory(category);

        return product;
    }

    @Override
    public String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/products/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            // Tạo tên file duy nhất
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            try {
                // Sử dụng File để lưu ảnh
                File destinationFile = new File(uploadPath, uniqueFileName);
                file.transferTo(destinationFile);
                // Cập nhật đường dẫn ảnh vào DTO (dùng để hiển thị)
                //productDto.setImagePath("/uploads/products/" + uniqueFileName);
                String path = "/uploads/products/" + uniqueFileName;
                return path;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String editFile(MultipartFile file, String oldImage) {
        if (file != null && !file.isEmpty()) {
            //xoa anh cu
            String fullPath = System.getProperty("user.dir") + "/src/main/resources/static" + oldImage;
            File imageFile = new File(fullPath);
            if (imageFile.exists()) {
                imageFile.delete();
            }

            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/products/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            // Tạo tên file duy nhất
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            try {
                // Sử dụng File để lưu ảnh
                File destinationFile = new File(uploadPath, uniqueFileName);
                file.transferTo(destinationFile);
                // Cập nhật đường dẫn ảnh vào DTO (dùng để hiển thị)
                //productDto.setImagePath("/uploads/products/" + uniqueFileName);
                String path = "/uploads/products/" + uniqueFileName;
                return path;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return oldImage;
    }

    @Override
    public Long findMaxProductId() {
        Long maxProductId = 0L;
        if (productRepository.findMaxProductId() == null) {
            return maxProductId;
        }
        return productRepository.findMaxProductId();
    }

    @Override
    public void deleteProductById(Long id) {
        List<Product> products = productRepository.findByProductId_id(id);
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                String imagePath = product.getProductImage();
                String fullPath = System.getProperty("user.dir") + "/src/main/resources/static" + imagePath;
                File imageFile = new File(fullPath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }

                String thumbPath = product.getProductThumbnail();
                String fullThumbPath = System.getProperty("user.dir") + "/src/main/resources/static" + thumbPath;
                File thumbFile = new File(fullThumbPath);
                if (thumbFile.exists()) {
                    thumbFile.delete();
                }
            }
            productRepository.deleteAll(products);
        }
    }

    //copy file để lưu 2 file image, thumb giống nhau nhưng khác tên
    @Override
    public String copyFile(String existingPath) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static";
            String fullSourcePath = uploadDir + existingPath;

            String fileExtension = existingPath.substring(existingPath.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            String newPath = "/uploads/products/" + uniqueFileName;
            String fullTargetPath = uploadDir + newPath;

            Files.copy(Paths.get(fullSourcePath), Paths.get(fullTargetPath));
            return newPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<Product> getAllProducts(String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByProductId_Language(language, pageable);
    }

    @Override
    public Page<Product> findProductByProductNameAndLanguage(String productName, String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductByProductNameAndLanguage(productName, language, pageable);
    }

    @Override
    public Page<Product> findProductByCategory(Integer categoryId, String language, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductByCategoryId(categoryId, language, pageable);
    }

    @Override
    public List<Product> getAllProductByLanguage(String language) {
        return productRepository.findProductByProductId_Language(language);
    }
}
