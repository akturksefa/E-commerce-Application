package com.akturk.e_commerce.repository;

import com.akturk.e_commerce.enums.ERole;
import com.akturk.e_commerce.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {

    Optional<Role> findByName(ERole name);
    boolean existsByName(ERole name);
}
