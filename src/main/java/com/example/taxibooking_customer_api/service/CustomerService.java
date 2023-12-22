package com.example.taxibooking_customer_api.service;

import com.example.taxibooking_customer_api.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface CustomerService {
    public ResponseEntity<?> customer_register(CustomerRegisterDTO CustomerRegisterDTO);

    public ResponseEntity<?> getCustomerPinNumber(CustomerMobileDTO OtpDTO);

    ResponseEntity<?> verifyCustomerMobile(VerifyRequestDTO verifyRequestDTO);

    ResponseEntity<?> updateProfile(CustomerUpdateProfileDTO customerUpdateProfileDTO);

    ResponseEntity<?> updateHomeOfficeAddress(UpdateAddressDTO updateAddressDTO);

    ResponseEntity<?> removeHomeOfficeAddress(RemoveAddressDTO removeAddressDTO);

    ResponseEntity<?> updateProfileImage(String updateProfileImageDTO, MultipartFile multipartFile) throws IOException;
}
