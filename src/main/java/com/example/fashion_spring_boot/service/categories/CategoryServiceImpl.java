package com.example.fashion_spring_boot.service.categories;

import com.example.fashion_spring_boot.dao.CategoryRepository;
import com.example.fashion_spring_boot.dto.CategoryDto;
import com.example.fashion_spring_boot.entity.Category;
import com.example.fashion_spring_boot.exception.CategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Category category) {
        categoryRepository.saveAndFlush(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category convertCategoryDtoToCategory(CategoryDto dto) {
        Category category;
        if (dto.getId() > 0) {
            category = categoryRepository.findById(dto.getId()).get();
        } else {
            category = new Category();
        }
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryImage(dto.getImagePath());
        return category;
    }

    @Override
    public String saveFile(MultipartFile file, String oldImage) {
        if (file != null && !file.isEmpty()) {
            //xoa anh cu
            String fullPath = System.getProperty("user.dir") + "/src/main/resources/static" + oldImage;
            File imageFile = new File(fullPath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/categories";
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
                String path = "/uploads/categories/" + uniqueFileName;

                return path;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return oldImage;
    }

    @Override
    public void deleteFile(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            try {
                // Thử xóa trong DB trước
                categoryRepository.delete(category);

                // Nếu không lỗi, mới xóa ảnh
                String imagePath = category.getCategoryImage();
                String fullPath = System.getProperty("user.dir") + "/src/main/resources/static" + imagePath;
                File imageFile = new File(fullPath);
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            } catch (Exception e) {
                // Nếu lỗi, không xóa ảnh, ném ra lỗi hợp lệ
                throw new CategoryException("Cannot be deleted, related products!");
            }
        }
    }

}
