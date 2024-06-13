package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.CategoryAlreadyExistsException;
import com.akturk.e_commerce.exceptions.CategoryNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.model.Category;
import com.akturk.e_commerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryServiceImpl {


    @Autowired
    CategoryRepository categoryRepository;


    public ResponseEntity<ApiResponseDto<?>> getAllCategories() throws ServiceLogicException {
        List<Category> categories = categoryRepository.findAll();
        try {
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .response(categories)
                            .message(categories.size() + " results found!")
                            .build()
            );
        }catch (Exception e) {
            throw new ServiceLogicException("Unable to find categories!");
        }
    }


    public ResponseEntity<ApiResponseDto<?>> getCategoryById(String categoryId) throws ServiceLogicException {

        try {
            Category category = null;
            if (categoryRepository.existsById(categoryId)) {
                category = categoryRepository.findById(categoryId).orElse(null);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .response(category)
                                .build()
                );
            }
        }catch (Exception e) {
            throw new ServiceLogicException("Unable to find categories!");
        }
        throw new CategoryNotFoundException("No category found with id " + categoryId);
    }


    public ResponseEntity<ApiResponseDto<?>> createCategory(String name) throws ServiceLogicException, CategoryAlreadyExistsException {
        try {
            if (!categoryRepository.existsByCategoryName(name)) {
                Category category = Category.builder()
                        .categoryName(name)
                        .build();
                categoryRepository.insert(category);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Category saved successfully!")
                                .build()
                );
            }

        }catch (Exception e) {
            throw new ServiceLogicException("Unable save category!");
        }
        throw new CategoryAlreadyExistsException("Category already exists with name" + name);
    }
}
