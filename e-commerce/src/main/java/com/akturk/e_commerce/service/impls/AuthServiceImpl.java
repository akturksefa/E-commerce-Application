package com.akturk.e_commerce.service.impls;

import com.akturk.e_commerce.dto.requests.SignUpRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserAlreadyExistsException;
import com.akturk.e_commerce.factories.RoleFactory;
import com.akturk.e_commerce.model.Role;
import com.akturk.e_commerce.model.User;
import com.akturk.e_commerce.repository.UserRepository;
import com.akturk.e_commerce.service.AuthService;
import com.akturk.e_commerce.service.NotificationService;
import com.akturk.e_commerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import javax.management.relation.RoleNotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    RoleFactory roleFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.verificationCodeExpirationMs}")
    private long EXPIRY_PERIOD;


    public ResponseEntity<ApiResponseDto<?>> save(SignUpRequestDto signUpRequestDto)
            throws UserAlreadyExistsException, ServiceLogicException {
        if (userService.ExistsByUserName(signUpRequestDto.getUserName())) {
            throw new UserAlreadyExistsException("Registration Failed: username is already taken!");
        }
        if (userService.ExistsByEmail(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: email is already taken!");
        }

        try {
            User user = createUser(signUpRequestDto);
            userRepository.insert(user);
            notificationService.sendUserRegistrationVerificationEmail(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponseDto.builder().isSuccess(true)
                            .message("User account created successfully!")
                            .build()
            );

        }catch(Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            throw new ServiceLogicException("Registration failed: Something went wrong!");
        }

    }


    private User createUser(SignUpRequestDto signUpRequestDto) throws RoleNotFoundException {
        return User.builder()
                .email(signUpRequestDto.getEmail())
                .username(signUpRequestDto.getUserName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .enabled(true)
                .roles(determineRoles(signUpRequestDto.getRoles()))
                .build();
    }

    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    private Date calculateCodeExpirationTime() {
        long currentTimeInMs = System.currentTimeMillis();
        return new Date(currentTimeInMs + EXPIRY_PERIOD);
    }

    private Set<Role> determineRoles(Set<String> strRoles) throws RoleNotFoundException {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(roleFactory.getInstance("user"));
        } else {
            for (String role : strRoles) {
                roles.add(roleFactory.getInstance(role));
            }
        }
        return roles;
    }



}
















