package com.example.taxibooking_customer_api.service.serviceImpl;

import com.example.taxibooking_customer_api.dto.*;
import com.example.taxibooking_customer_api.entity.Customer;
import com.example.taxibooking_customer_api.entity.CustomerTrip;
import com.example.taxibooking_customer_api.entity.RiderRating;
import com.example.taxibooking_customer_api.repository.CustomerRepository;
import com.example.taxibooking_customer_api.repository.RateRiderRepository;
import com.example.taxibooking_customer_api.repository.TripRepository;
import com.example.taxibooking_customer_api.repository.VehicleTypeRepository;
import com.example.taxibooking_customer_api.service.CustomerNotificationService;
import com.example.taxibooking_customer_api.service.TripService;
import com.example.taxibooking_customer_api.util.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerNotificationService customerNotificationService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;

    @Autowired
    RateRiderRepository rateRiderRepository;


    @Override
    public ResponseEntity<?> requestNewTripByCustomer(NewTripRequestDTO newTripRequestDTO) {
        if (newTripRequestDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("verification not available");
        } else if (String.valueOf(newTripRequestDTO.getVehicleTypeId()).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VehicleTypeId not available");
        } else if (newTripRequestDTO.getStartLat().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StartLat not available");
        } else if (newTripRequestDTO.getStartLon().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StartLon not available");
        } else if (newTripRequestDTO.getCustomerNote().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EndLon not available");
        } else if (newTripRequestDTO.getEndLon().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EndLat not available");
        } else if (newTripRequestDTO.getEndLat().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CustomerNote not available");
        } else if (newTripRequestDTO.getDistance().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Distance not available");
        } else if (newTripRequestDTO.getStartPoint().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("StartPoint not available");
        } else if (newTripRequestDTO.getEndPoint().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EndPoint not available");
        } else {

            Optional<Customer> optionalCustomer = customerRepository.findById(requestMetaDTO.getCustomerId());
            if (optionalCustomer.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Id Invalid");
            }

            Customer customer = optionalCustomer.get();
            if (!customer.getVerification().equals(newTripRequestDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Validation");
            }

            if (optionalCustomer.isPresent()) {

                CustomerTrip customerTrip = new CustomerTrip();
                customerTrip.setCustomerId(customer.getCustomerId());
                customerTrip.setVehicleTypeId(newTripRequestDTO.getVehicleTypeId());
                customerTrip.setStartLat(newTripRequestDTO.getStartLat());
                customerTrip.setStartLon(newTripRequestDTO.getStartLon());
                customerTrip.setEndLat(newTripRequestDTO.getEndLat());
                customerTrip.setStartLat(newTripRequestDTO.getStartLat());
                customerTrip.setCustomerNote(newTripRequestDTO.getCustomerNote());
                customerTrip.setTripDistance(Double.parseDouble(newTripRequestDTO.getDistance()));
                customerTrip.setStartPoint(newTripRequestDTO.getStartPoint());
                customerTrip.setEndPoint(newTripRequestDTO.getEndPoint());
                customerTrip.setTripDate(String.valueOf(LocalDate.now()));
                customerTrip.setTripTime(String.valueOf(LocalTime.now()));
                tripRepository.save(customerTrip);

                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        customerRepository.findByMobile1(requestMetaDTO.getMobile()).get().getCustomerId(),
                        "Successfully new Trip requested",
                        "New Trip requested",
                        1));

                return ResponseEntity.status(HttpStatus.OK).body("Success");

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Id Invalid");
            }

        }

    }

    @Override
    public ResponseEntity<?> rejectTrip(int tripId, RejectTripDTO rejectTripDTO) {
        if (String.valueOf(tripId).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not found");
        } else if (String.valueOf(rejectTripDTO.getVerification()).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification not available");
        } else if (rejectTripDTO.getReason().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reason not available");
        } else if (tripRepository.findById(tripId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not available");
        } else {
            Customer customer = customerRepository.findById(requestMetaDTO.getCustomerId()).get();
            if (!customer.getVerification().equals(rejectTripDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Verification");
            }

            CustomerTrip customerTrip = tripRepository.findById(tripId).get();
            customerTrip.setRejectReason(rejectTripDTO.getReason());
            customerTrip.setRejectDate(String.valueOf(LocalDate.now()));
            customerTrip.setRejectTime(String.valueOf(LocalTime.now()));
            customerTrip.setRejectUserType(1);
            customerTrip.setTripStatus(6);

            customerNotificationService.saveNotification(new CustomerNotificationDTO(
                    customerRepository.findByMobile1(requestMetaDTO.getMobile()).get().getCustomerId(),
                    "Trip rejected Successfully",
                    "Trip rejected request",
                    1));

            tripRepository.save(customerTrip);

            return ResponseEntity.status(HttpStatus.OK).body("Success!");
        }
    }

    @Override
    public ResponseEntity<?> checkCustomerTripAcceptedByRider(int tripId, VerificationCodeDTO verificationCodeDTO) {
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not found");
        } else if (String.valueOf(tripId).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not found");
        } else if (customerRepository.findById(tripId).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not available");
        } else if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verify not available");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("In Valid Verification");
        } else {
            CustomerTrip customerTrip = tripRepository.findById(tripId).get();
            if (String.valueOf(customerTrip.getTripStatus()).equals("0")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "Pending your are trip (Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "trip Pending",
                        1));
                return ResponseEntity.status(HttpStatus.OK).body("pending");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("1")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "Give a new trip (Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "Search For rider",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Search for rider");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("2")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "Rider Success(Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "rider assigned",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("rider assigned");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("3")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "Search place(Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "trip start",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("trip start");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("4")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "trip finished Successfully(Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "trip finished",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("trip end");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("5")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "No Cancelled(Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "trip cancel by rider",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("trip cancel by rider");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("6")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "You Cancelled Trip(Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "trip cancel by customer",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("trip cancel by customer");
            } else if (String.valueOf(customerTrip.getTripStatus()).equals("7")) {
                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        requestMetaDTO.getCustomerId(),
                        "All Rider are Busy for the Newly Requested(Trip Id:" + customerTrip.getCustomerTripId() +")",
                        "ride no found for trip",
                        1));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ride no found for trip");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Something went wrong!");
        }
    }

    @Override
    public ResponseEntity<?> getTripDataCustomerById(int tripId, VerificationCodeDTO verificationCodeDTO) {
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not found");
        } else if (String.valueOf(tripId).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not found");
        } else if (customerRepository.findById(tripId).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id not available");
        } else if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verify not available");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("In Valid Verification");
        } else {
            CustomerTrip customerTrip = tripRepository.findById(tripId).get();
            return ResponseEntity.status(HttpStatus.OK).body(customerTrip);
        }
    }

    @Override
    public ResponseEntity<?> rateRider(int tripId, RateRideDTO rateRideDTO) {
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id not found");
        } else if (String.valueOf(tripId).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id Not found");
        } else if (customerRepository.findById(tripId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Trip Id");
        } else if (rateRideDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(rateRideDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("In Valid Verification");
        } else if (rateRideDTO.getStarRate().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found");
        } else if (rateRideDTO.getComment().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comments not available");
        } else {
            RiderRating riderRating = new RiderRating();
            riderRating.setCustomerTripId(requestMetaDTO.getCustomerId());
            riderRating.setDate(String.valueOf(LocalDate.now()));
            riderRating.setTime(String.valueOf(LocalTime.now()));
            riderRating.setStarRate(rateRideDTO.getStarRate());
            riderRating.setComment(rateRideDTO.getComment());

            customerNotificationService.saveNotification(new CustomerNotificationDTO(
                    customerRepository.findByMobile1(requestMetaDTO.getMobile()).get().getCustomerId(),
                    "Rider rated Successfully",
                    "Rider rating",
                    1));

            rateRiderRepository.save(riderRating);
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
    }

    @Override
    public ResponseEntity<?> getTripHistoryByCustomer(RequestTripHistoryDTO requestTripHistoryDTO) {
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id not found");
        } else if (requestTripHistoryDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(requestTripHistoryDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        } else {
            if (requestTripHistoryDTO.getDate().equals("")) {
                List<CustomerTrip> todayTrips = tripRepository.findAllByCustomerIdAndTripDate(requestMetaDTO.getCustomerId(), String.valueOf(LocalDate.now()));
                return ResponseEntity.status(HttpStatus.OK).body(todayTrips);
            } else {

                String trpDataString = requestTripHistoryDTO.getDate();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                List<CustomerTrip> customerTrips = new ArrayList<>();
                try {
                    Date date = dateFormat.parse(trpDataString);
                    List<CustomerTrip> allTrips = tripRepository.findAllByCustomerId(requestMetaDTO.getCustomerId());
                    allTrips.forEach(trip -> {
                        try {
                            Date tripDate = dateFormat.parse(trip.getTripDate());
                            if (tripDate.equals(date) || tripDate.after(date)) {
                                customerTrips.add(trip);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                    return ResponseEntity.status(HttpStatus.OK).body(customerTrips);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

//    @Override
//    public ResponseEntity<?> getCustomerRecentVisitedDestination(VerificationCodeDTO verificationCodeDTO){
//        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id not found");
//        } else if (verificationCodeDTO.getVerification().equals("")) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not found");
//        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
//                .get().getVerification().equals(verificationCodeDTO.getVerification())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
//        }else {
//            List<CustomerTrip> trips = tripRepository.findAllByCustomerId(requestMetaDTO.getCustomerId());
//            trips.forEach(trip -> {
//
//            });
//        }
//    }

}