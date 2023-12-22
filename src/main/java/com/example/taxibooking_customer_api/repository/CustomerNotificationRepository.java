package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.CustomerNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNotificationRepository extends JpaRepository<CustomerNotification, Integer> {

    List<CustomerNotification> findAllByCustomerIdAndShowToUser(int customerId, int i);
}
