package com.akturk.e_commerce.service;

import com.akturk.e_commerce.exceptions.UserNotFoundException;
import com.akturk.e_commerce.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {

    boolean ExistsByUserName(String userName);
    boolean ExistsByEmail(String email);
    User findByEmail(String email) throws UserNotFoundException;

}
