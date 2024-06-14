package com.akturk.e_commerce.factories;

import com.akturk.e_commerce.enums.ERole;
import com.akturk.e_commerce.model.Role;
import com.akturk.e_commerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;

@Component
public class RoleFactory {

    @Autowired
    private RoleService roleService;
    public Role getInstance(String role) throws RoleNotFoundException {
        if (role.equals("admin")) {
            return roleService.findByName(ERole.ROLE_ADMIN);
        }
        else if (role.equals("user")){
            return roleService.findByName(ERole.ROLE_USER);
        }
        throw new RoleNotFoundException("Invalid role name: " + role);
    }
}
