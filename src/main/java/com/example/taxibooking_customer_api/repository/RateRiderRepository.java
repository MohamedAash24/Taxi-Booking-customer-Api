package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.CustomerTrip;
import com.example.taxibooking_customer_api.entity.RiderRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRiderRepository extends JpaRepository<RiderRating, Integer> {
}
