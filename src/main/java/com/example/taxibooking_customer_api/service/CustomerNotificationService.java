package com.example.taxibooking_customer_api.service;

import com.example.taxibooking_customer_api.dto.CustomerNotificationDTO;
import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CustomerNotificationService {

    void  saveNotification(CustomerNotificationDTO customerNotificationDTO);

    ResponseEntity<?> getCustomerNotification(VerificationCodeDTO customerNotificationDTO);
}
