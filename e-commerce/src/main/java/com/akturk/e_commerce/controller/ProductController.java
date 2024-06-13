package com.akturk.e_commerce.controller;

import com.akturk.e_commerce.dto.requests.ProductRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ProductNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    ResponseEntity<ApiResponseDto<?>> addProduct (@RequestBody ProductRequestDto requestDto) throws ServiceLogicException{
        return productService.addProduct(requestDto);
    }

    @GetMapping("/get/all")
    ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException{
        return productService.getAllProducts();
    }
    @GetMapping("/get/byId")
    ResponseEntity<ApiResponseDto<?>> getProductById(@RequestParam String id) throws ServiceLogicException, ProductNotFoundException {
        return productService.getProductById(id);
    }

    @GetMapping("/get/byCategory")
    ResponseEntity<ApiResponseDto<?>> getProductByCategory(@RequestParam String id) throws ServiceLogicException{
        return productService.getProductByCategory(id);
    }

    @GetMapping("/search")
    ResponseEntity<ApiResponseDto<?>> searchProducts(@RequestParam String searchKey) throws ServiceLogicException {
        return productService.searchProducts(searchKey);
    }
}
