package com.akturk.e_commerce.repository;

import com.akturk.e_commerce.model.Category;
import com.akturk.e_commerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends MongoRepository<Product,String> {

    List<Product> findByCategory(Category category);
    List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategory_CategoryNameContainingIgnoreCase(String ProductName, String description, String categoryName);

}
