package com.example.taxibooking_customer_api.service;

import com.example.taxibooking_customer_api.dto.SendMessageDTO;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    ResponseEntity<?> sendMessage(SendMessageDTO sendMessageDTO);
}
