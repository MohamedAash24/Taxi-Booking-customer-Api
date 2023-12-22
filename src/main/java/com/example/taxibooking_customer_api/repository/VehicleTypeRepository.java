package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer > {
}
