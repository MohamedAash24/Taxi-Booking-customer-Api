//package com.example.taxibooking_customer_api.controller;
//
//import com.example.taxibooking_customer_api.dto.LoginDTO;
//import com.example.taxibooking_customer_api.service.serviceImpl.AdminServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class LoginController {
//    @Autowired
//    AdminServiceImpl adminServiceImpl;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
//
//        return adminServiceImpl.login(loginDTO);
//    }
//
//}
