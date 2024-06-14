package com.akturk.e_commerce.service;

import com.akturk.e_commerce.model.Order;
import com.akturk.e_commerce.model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface NotificationService {

      void sendUserRegistrationVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    void sendOrderConfirmationEmail(User user, Order order) throws MessagingException, UnsupportedEncodingException ;

}
