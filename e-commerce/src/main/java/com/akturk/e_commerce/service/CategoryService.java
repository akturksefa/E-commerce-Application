package com.akturk.e_commerce.service;


import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.CategoryAlreadyExistsException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    ResponseEntity<ApiResponseDto<?>> getAllCategories() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getCategoryById(String categoryId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> createCategory(String name) throws ServiceLogicException, CategoryAlreadyExistsException;

}