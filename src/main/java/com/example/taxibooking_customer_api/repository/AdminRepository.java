package com.example.taxibooking_customer_api.repository;

import com.example.taxibooking_customer_api.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository <Admin, Integer> {

    Admin findByUsername(String username);

    Admin findOneByUsernameAndPassword(String username, String password);
}
