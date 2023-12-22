package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.Admin;
import com.example.taxibooking_customer_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByMobile1(String mobile);
}
