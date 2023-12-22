package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.CustomerTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<CustomerTrip, Integer> {
    List<CustomerTrip> findAllByCustomerId(int customerId);

    List<CustomerTrip> findAllByCustomerIdAndTripDate(int customerId, String valueOf);
}
