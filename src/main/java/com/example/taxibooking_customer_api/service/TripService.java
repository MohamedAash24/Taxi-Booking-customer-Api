package com.example.taxibooking_customer_api.service;

import com.example.taxibooking_customer_api.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TripService {
    public ResponseEntity<?> requestNewTripByCustomer(NewTripRequestDTO newTripRequestDTO);

    ResponseEntity<?> rejectTrip(int tripId,RejectTripDTO rejectTripDTO);

    ResponseEntity<?> checkCustomerTripAcceptedByRider(int tripId, VerificationCodeDTO verificationCodeDTO);

    ResponseEntity<?> getTripDataCustomerById(int tripId, VerificationCodeDTO verificationCodeDTO);

    ResponseEntity<?> rateRider(int tripId, RateRideDTO rateRideDTO);

    ResponseEntity<?> getTripHistoryByCustomer(RequestTripHistoryDTO requestTripHistoryDTO);

//    ResponseEntity<?> getCustomerRecentVisitedDestination(VerificationCodeDTO verificationCodeDTO);
}
