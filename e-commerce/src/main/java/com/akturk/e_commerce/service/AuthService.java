package com.akturk.e_commerce.service;

import com.akturk.e_commerce.dto.requests.SignUpRequestDto;
import com.akturk.e_commerce.dto.responses.ApiResponseDto;
import com.akturk.e_commerce.exceptions.ServiceLogicException;
import com.akturk.e_commerce.exceptions.UserAlreadyExistsException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface AuthService {

    ResponseEntity<ApiResponseDto<?>> save(SignUpRequestDto signUpRequestDto) throws MessagingException, UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException;
}
