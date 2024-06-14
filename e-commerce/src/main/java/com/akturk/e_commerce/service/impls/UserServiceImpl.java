package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.exceptions.UserNotFoundException;
import com.akturk.e_commerce.model.User;
import com.akturk.e_commerce.repository.UserRepository;
import com.akturk.e_commerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean ExistsByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

    public boolean ExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email " +  email));
    }
}
