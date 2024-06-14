package com.akturk.e_commerce.controller;

import com.akturk.e_commerce.dto.requests.CategoryRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.CategoryAlreadyExistsException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/create")
    ResponseEntity<ApiResponseDto<?>> createCategory (@RequestBody CategoryRequestDto categoryRequestDto) throws CategoryAlreadyExistsException, ServiceLogicException
    {
      return categoryService.createCategory(categoryRequestDto.getName());

    }

    @GetMapping("/get/all")
    ResponseEntity<ApiResponseDto<?>> getAllCategories () throws ServiceLogicException
    {
      return  categoryService.getAllCategories();
    }
    @GetMapping("/get/byId")
    ResponseEntity<ApiResponseDto<?>> getCategoryById(@RequestParam String id) throws ServiceLogicException
    {
        return categoryService.getCategoryById(id);
    }
}
