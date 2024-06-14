package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.enums.ERole;
import com.akturk.e_commerce.model.Role;
import com.akturk.e_commerce.repository.RoleRepository;
import com.akturk.e_commerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(ERole eRole) {
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Role is not found."));

    }
}
