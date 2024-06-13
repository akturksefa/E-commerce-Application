package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.dto.requests.ProductRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.dto.responses.ProductResponseDto;
import com.akturk.e_commerce.exceptions.CategoryNotFoundException;
import com.akturk.e_commerce.exceptions.ProductNotFoundException;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.model.Product;
import com.akturk.e_commerce.repository.CategoryRepository;
import com.akturk.e_commerce.repository.ProductRepository;
import com.akturk.e_commerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    public ResponseEntity<ApiResponseDto<?>> addProduct(ProductRequestDto requestDto) throws ServiceLogicException {
        try {
            if (categoryRepository.existsById(requestDto.getCategoryId())) {
                Product product = productDtoToProduct(requestDto);
                productRepository.insert(product);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .message("Product saved succesfully!")
                                .build()
                );
            }
        } catch (Exception e) {
            throw new ServiceLogicException("Unable save category!");
        }
        throw new CategoryNotFoundException("Category not found with id " + requestDto.getCategoryId());
    }

    public ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException {

        try {
            List<Product> products = productRepository.findAll();
            List<ProductResponseDto> productResponse = new ArrayList<>();

            for (Product product: products){
                productResponse.add(productToProductDto(product));
            }
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .response(productResponse)
                            .message(products.size() + "results found")
                            .build()
            );
        }catch (Exception e) {
            throw new ServiceLogicException("Unable to find products!");
        }


    }

    public ResponseEntity<ApiResponseDto<?>> getProductById(String productId) throws ServiceLogicException, ProductNotFoundException {
        try {
            if (productRepository.existsById(productId)) {
                Product product = productRepository.findById(productId).orElse(null);
                ProductResponseDto productResponse = productToProductDto(product);
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .response(productResponse)
                                .message(" One result found!")
                                .build()
                );
            }

        }catch (Exception e) {
            throw new ServiceLogicException("Unable to find products!");
        }
        throw new ProductNotFoundException("No product found with id " + productId);
    }

    public ResponseEntity<ApiResponseDto<?>> getProductByCategory(String categoryId) throws ServiceLogicException {
        try {
            if (categoryRepository.existsById(categoryId)){
                List<Product> products = productRepository.findByCategory(categoryRepository.findById(categoryId).orElse(null));
                List<ProductResponseDto> productResponse = new ArrayList<>();

                for(Product product: products){
                    productResponse.add(productToProductDto(product));
                }
                return ResponseEntity.ok(
                        ApiResponseDto.builder()
                                .isSuccess(true)
                                .response(productResponse)
                                .message(products.size() + " results found!")
                                .build()
                );
            }

        }catch (Exception e) {
            throw new ServiceLogicException("Unable to find products!");
        }
        throw new CategoryNotFoundException("Category not found with id " + categoryId);
    }

    public ResponseEntity<ApiResponseDto<?>> searchProducts(String searchKey) throws ServiceLogicException {
        try {
            List<Product> products = productRepository
                    .findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(searchKey, searchKey, searchKey);
            List<ProductResponseDto> productResponse = new ArrayList<>();

            for(Product product: products){
                productResponse.add(productToProductDto(product));
            }
            return ResponseEntity.ok(
                    ApiResponseDto.builder()
                            .isSuccess(true)
                            .response(productResponse)
                            .message(products.size() + " results found!")
                            .build()
            );

        }catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceLogicException("Unable to find products!");
        }
    }

    private ProductResponseDto productToProductDto(Product product) {
        return  ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory().getCategoryName())
                .build();

    }


    private Product productDtoToProduct(ProductRequestDto requestDto) {
        return Product.builder()
                .productName(requestDto.getProductName())
                .price(requestDto.getPrice())
                .description(requestDto.getDescription())
                .imageUrl(requestDto.getImageUrl())
                .category(categoryRepository.findById(requestDto.getCategoryId()).orElse(null))
                .build();
    }
}










