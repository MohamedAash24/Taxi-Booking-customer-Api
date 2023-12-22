package com.example.taxibooking_customer_api.service;

import com.example.taxibooking_customer_api.dto.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    public ResponseEntity<?> login(LoginDTO loginDTO);

    public boolean findByUsername(LoginDTO loginDTO);
}
