package com.akturk.e_commerce.repository;

import com.akturk.e_commerce.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {

    boolean existsByCategoryName(String categoryName);
}
