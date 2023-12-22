package com.example.taxibooking_customer_api.controller;

import com.example.taxibooking_customer_api.dto.*;
import com.example.taxibooking_customer_api.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    TripService tripService;

    @PostMapping("/new_trip/{app_id}")
    public ResponseEntity<?> requestNewTripByCustomer(@PathVariable("app_id")String appId, @RequestBody NewTripRequestDTO newTripRequestDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  tripService.requestNewTripByCustomer(newTripRequestDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("App Id not available");
        }
    }

    @PostMapping("/reject_trip/{trip_id}/{app_id}")
    public ResponseEntity<?> customerRejectTrip(@PathVariable("trip_id")int tripId,@PathVariable("app_id")String appId, @RequestBody RejectTripDTO rejectTripDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  tripService.rejectTrip(tripId,rejectTripDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/trip_status/{trip_id}/{app_id}")
    public ResponseEntity<?> checkCustomerTripAcceptedByRider(@PathVariable("app_id")String appId,@PathVariable("trip_id")int tripId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  tripService.checkCustomerTripAcceptedByRider(tripId, verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/trip_cancle_auto_/{trip_id}/{app_id}")
    public ResponseEntity<?> autoCancelTripByCustomer (@PathVariable("app_id")String appId,@PathVariable("trip_id")int tripId, @RequestBody VerificationCodeDTO verificationCodeDTO){
            if (appId.equals("novateachzone_customer_app")){
//                return  tripService.autoCancelTripByCustomer(tripId, verificationCodeDTO);
                return  null;
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
            }
    }

    @PostMapping("/trip_idata/{trip_id}/{app_id}")
    public ResponseEntity<?> getTripDataCustomerById(@PathVariable("app_id")String appId,@PathVariable("trip_id")int tripId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novateachzone_customer_app")){
                return  tripService.getTripDataCustomerById(tripId, verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/rate_rider/{trip_id}/{app_id}")
    public ResponseEntity<?> rateRider(@PathVariable("app_id")String appId,@PathVariable("trip_id")int tripId, @RequestBody RateRideDTO rateRideDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  tripService.rateRider(tripId,rateRideDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Rate");
        }
    }

    @GetMapping("/history/{app_id}")
    public ResponseEntity<?>getTripHistoryByCustomer(@PathVariable("app_id") String appId,@RequestBody RequestTripHistoryDTO requestTripHistoryDTO){
        if (appId.equals("novateachzone_customer_app")){
            return  tripService.getTripHistoryByCustomer(requestTripHistoryDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Rate");
        }
    }

//    @GetMapping("/trip_recent/{app_id}")
//    public ResponseEntity<?>getCustomerRecentVisitedDestination(@PathVariable("app_id") String appId,@RequestBody VerificationCodeDTO verificationCodeDTO){
//        if (appId.equals("novateachzone_customer_app")){
//            return  tripService.getCustomerRecentVisitedDestination(verificationCodeDTO);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Rate");
//        }
//    }
}
