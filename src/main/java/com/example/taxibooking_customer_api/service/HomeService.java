package com.example.taxibooking_customer_api.service;

import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import com.example.taxibooking_customer_api.dto.VerifyRequestDTO;
import org.springframework.http.ResponseEntity;

public interface HomeService {
    ResponseEntity<?> checkCustomerOnGoingTrip(VerificationCodeDTO verificationCodeDTO);
}
