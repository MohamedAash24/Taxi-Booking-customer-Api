package com.example.taxibooking_customer_api.service;

import org.springframework.http.ResponseEntity;

public interface VehicleService {
    ResponseEntity<?> getVehicleTypes();
}
