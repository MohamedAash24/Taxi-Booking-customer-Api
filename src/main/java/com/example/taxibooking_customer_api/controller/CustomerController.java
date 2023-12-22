package com.example.taxibooking_customer_api.controller;

import com.example.taxibooking_customer_api.dto.*;
import com.example.taxibooking_customer_api.service.CustomerService;
import com.example.taxibooking_customer_api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    MessageService messageService;

    @PostMapping("/register/{app_id}")
    public ResponseEntity<?> customer_register(@PathVariable("app_id") String appId,@RequestBody CustomerRegisterDTO customerRegisterDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  customerService.customer_register(customerRegisterDTO);
    } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @PostMapping("/register/otp/{app_id}")
    public ResponseEntity<?> getCustomerPinNumber(@PathVariable("app_id")String appId, @RequestBody CustomerMobileDTO customerMobileDTO){
        if (appId.equals("novateachzone_customer_app")){
            return customerService.getCustomerPinNumber(customerMobileDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @PostMapping("/register/verify/{app_id}")
    public ResponseEntity<?> verifyCustomerMobile(@PathVariable("app_id")String appId, @RequestBody VerifyRequestDTO verifyRequestDTO){
        if (appId.equals("novateachzone_customer_app")){
            return customerService.verifyCustomerMobile(verifyRequestDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @PostMapping("/profile/{app_id}")
    public ResponseEntity<?> updateProfile(@PathVariable("app_id")String appId , @RequestBody CustomerUpdateProfileDTO customerUpdateProfileDTO){
        if (appId.equals("novateachzone_customer_app")){
            return customerService.updateProfile(customerUpdateProfileDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update Profile not available");
        }
    }

    @PostMapping("/update_adders/{app_id}")
    public ResponseEntity<?> updateHomeOfficeAddress(@PathVariable("app_id")String appId , @RequestBody UpdateAddressDTO updateAddressDTO){
        if (appId.equals("novateachzone_customer_app")){
            return customerService.updateHomeOfficeAddress(updateAddressDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @DeleteMapping("/remove_adders/{app_id}")
    public ResponseEntity<?> removeHomeOfficeAddress(@PathVariable("app_id")String appId , @RequestBody RemoveAddressDTO removeAddressDTO){
        if (appId.equals("novateachzone_customer_app")){
            return customerService.removeHomeOfficeAddress(removeAddressDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @PostMapping("/send_massage/{app_id}")
    public ResponseEntity<?> sendMessage(@PathVariable("app_id")String appId , @RequestBody SendMessageDTO sendMessageDTO){
        if (appId.equals("novateachzone_customer_app")){
            return messageService.sendMessage(sendMessageDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @PostMapping("/update_profile_img/{app_id}")
    public ResponseEntity<?>updateProfileImage(@PathVariable("app_id")String appId , @RequestParam String verification,
                                                @RequestParam("image")MultipartFile multipartFile) throws IOException {
        if (appId.equals("novateachzone_customer_app")){
            return customerService.updateProfileImage(verification, multipartFile);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

}
