package com.example.taxibooking_customer_api.controller;

import com.example.taxibooking_customer_api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping("/vehicle_types/{app_id}")
        public ResponseEntity<?> getVehicleTypes(@PathVariable("app_id") String appId ){
            if (appId.equals("novateachzone_customer_app")){
                return vehicleService.getVehicleTypes();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
            }

        }
}
