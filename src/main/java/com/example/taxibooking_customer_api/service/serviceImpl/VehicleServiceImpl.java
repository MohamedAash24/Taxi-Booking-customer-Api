package com.example.taxibooking_customer_api.service.serviceImpl;

import com.example.taxibooking_customer_api.entity.VehicleType;
import com.example.taxibooking_customer_api.repository.VehicleTypeRepository;
import com.example.taxibooking_customer_api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Override
    public ResponseEntity<?> getVehicleTypes(){
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(vehicleTypes);
    }
}
