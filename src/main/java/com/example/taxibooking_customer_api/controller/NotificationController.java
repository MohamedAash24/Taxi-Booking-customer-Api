package com.example.taxibooking_customer_api.controller;

import com.example.taxibooking_customer_api.dto.CustomerNotificationDTO;
import com.example.taxibooking_customer_api.dto.CustomerRegisterDTO;
import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import com.example.taxibooking_customer_api.service.CustomerNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifcation")
public class NotificationController {

    @Autowired
    CustomerNotificationService customerNotificationService;
    @GetMapping("/customer_notification/{app_id}")
    public ResponseEntity<?> getCustomerNotification(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  customerNotificationService.getCustomerNotification(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }
}
