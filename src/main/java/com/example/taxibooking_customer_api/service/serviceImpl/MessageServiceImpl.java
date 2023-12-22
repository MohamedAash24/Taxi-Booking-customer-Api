package com.example.taxibooking_customer_api.service.serviceImpl;

import com.example.taxibooking_customer_api.dto.RequestMetaDTO;
import com.example.taxibooking_customer_api.dto.SendMessageDTO;
import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import com.example.taxibooking_customer_api.entity.CustomerContact;
import com.example.taxibooking_customer_api.entity.CustomerTrip;
import com.example.taxibooking_customer_api.repository.CustomerRepository;
import com.example.taxibooking_customer_api.repository.MessageServiceRepository;
import com.example.taxibooking_customer_api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageServiceRepository messageServiceRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;


    @Override
    public ResponseEntity<?> sendMessage(SendMessageDTO sendMessageDTO) {
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Customer Id");
        } else if (sendMessageDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(sendMessageDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        } else if (sendMessageDTO.getEmail().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        } else if (sendMessageDTO.getMessage().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        } else {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomerId(requestMetaDTO.getCustomerId());
            customerContact.setEmail(sendMessageDTO.getEmail());
            customerContact.setMessage(sendMessageDTO.getMessage());
            customerContact.setDate(String.valueOf(LocalDate.now()));
            customerContact.setTime(String.valueOf(LocalTime.now()));

            messageServiceRepository.save(customerContact);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }

    }
}
