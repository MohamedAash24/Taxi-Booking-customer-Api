package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.CustomerContact;
import com.example.taxibooking_customer_api.entity.RiderRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageServiceRepository extends JpaRepository<CustomerContact, Integer> {

}
