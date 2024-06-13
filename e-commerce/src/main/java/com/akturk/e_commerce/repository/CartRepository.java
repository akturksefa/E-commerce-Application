package com.akturk.e_commerce.repository;

import com.akturk.e_commerce.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {

    Cart findByUserId(String userId);
    boolean existsByUserId(String userId);

}
