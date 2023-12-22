package com.example.taxibooking_customer_api.controller;

import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import com.example.taxibooking_customer_api.dto.VerifyRequestDTO;
import com.example.taxibooking_customer_api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;
    @GetMapping("/ongoing_trip/{app_id}")
    public ResponseEntity<?> checkCustomerOnGoingTrip(@PathVariable("app_id")String appId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  homeService.checkCustomerOnGoingTrip(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }
}
