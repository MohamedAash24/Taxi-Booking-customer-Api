package com.example.taxibooking_customer_api.service.serviceImpl;

import com.example.taxibooking_customer_api.dto.RequestMetaDTO;
import com.example.taxibooking_customer_api.dto.VerificationCodeDTO;
import com.example.taxibooking_customer_api.dto.VerifyRequestDTO;
import com.example.taxibooking_customer_api.entity.CustomerTrip;
import com.example.taxibooking_customer_api.repository.CustomerRepository;
import com.example.taxibooking_customer_api.repository.TripRepository;
import com.example.taxibooking_customer_api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;


    @Override
    public ResponseEntity<?> checkCustomerOnGoingTrip(VerificationCodeDTO verificationCodeDTO) {
        if(customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Customer Id");
        }else if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        }else {
            List<CustomerTrip> customerTrips= tripRepository.findAllByCustomerId(requestMetaDTO.getCustomerId());
            List<CustomerTrip> onGoingTrips = new ArrayList<>();
            customerTrips.forEach(trips -> {
                if (String.valueOf(trips.getTripStatus()).equals("0") ||
                        String.valueOf(trips.getCustomerTripId()).equals("1") ||
                        String.valueOf(trips.getCustomerTripId()).equals("2") ||
                        String.valueOf(trips.getCustomerTripId()).equals("3")){
                    onGoingTrips.add(trips);
                }
            });
            return ResponseEntity.status(HttpStatus.OK).body(onGoingTrips);
        }
    }
}
