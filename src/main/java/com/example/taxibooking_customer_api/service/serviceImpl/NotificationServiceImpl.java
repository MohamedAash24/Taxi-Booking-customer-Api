package com.example.taxibooking_customer_api.service.serviceImpl;

import com.example.taxibooking_customer_api.dto.CustomerNotificationDTO;
import com.example.taxibooking_customer_api.dto.RequestMetaDTO;
import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import com.example.taxibooking_customer_api.entity.CustomerNotification;
import com.example.taxibooking_customer_api.entity.CustomerTrip;
import com.example.taxibooking_customer_api.repository.CustomerNotificationRepository;
import com.example.taxibooking_customer_api.repository.CustomerRepository;
import com.example.taxibooking_customer_api.service.CustomerNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements CustomerNotificationService {

    @Autowired
    CustomerNotificationRepository customerNotificationRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void saveNotification(CustomerNotificationDTO customerNotificationDTO) {
        CustomerNotification customerNotification = new CustomerNotification();
        customerNotification.setCustomerId(customerNotificationDTO.getCustomerId());
        customerNotification.setNotificationTopic(customerNotificationDTO.getNotificationTopic());
        customerNotification.setNotification(customerNotificationDTO.getNotification());
        customerNotification.setDate(String.valueOf(LocalDate.now()));
        customerNotification.setTime(String.valueOf(LocalTime.now()));
        customerNotification.setShowToUser(customerNotification.getShowToUser());

        customerNotificationRepository.save(customerNotification);
    }

    public ResponseEntity<?> getCustomerNotification(VerificationCodeDTO verificationCodeDTO){
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Customer Id");
        } else if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        } else {
            List<CustomerNotification> customerNotifications= customerNotificationRepository.findAllByCustomerIdAndShowToUser(requestMetaDTO.getCustomerId(), 1);
            return ResponseEntity.status(HttpStatus.OK).body(customerNotifications);
        }
    }

}
