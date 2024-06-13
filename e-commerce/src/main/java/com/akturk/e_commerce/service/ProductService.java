package com.akturk.e_commerce.service;

import com.akturk.e_commerce.dto.requests.ProductRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ProductNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ResponseEntity<ApiResponseDto<?>> addProduct(ProductRequestDto requestDto) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getProductById(String productId) throws ServiceLogicException, ProductNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getProductByCategory(String categoryId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> searchProducts(String searchKey) throws ServiceLogicException;



}
