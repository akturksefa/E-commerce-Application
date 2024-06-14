package com.akturk.e_commerce.service;

import com.akturk.e_commerce.enums.ERole;
import com.akturk.e_commerce.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findByName(ERole eRole);
}
