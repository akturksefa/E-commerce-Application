package com.akturk.e_commerce.controller;

import com.akturk.e_commerce.dto.requests.SignUpRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserAlreadyExistsException;
import com.akturk.e_commerce.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
public class SignUpController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws MessagingException, UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException {
        return authService.save(signUpRequestDto);
    }


}