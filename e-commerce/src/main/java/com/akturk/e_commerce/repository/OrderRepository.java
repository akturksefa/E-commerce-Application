package com.akturk.e_commerce.repository;

import com.akturk.e_commerce.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {

    Set<Order> findByUserIdOrderByIdDesc(String userId);
}
