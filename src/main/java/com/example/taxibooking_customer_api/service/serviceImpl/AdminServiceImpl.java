//package com.example.taxibooking_customer_api.service.serviceImpl;
//
//import com.example.taxibooking_customer_api.dto.LoginDTO;
//import com.example.taxibooking_customer_api.entity.Admin;
//import com.example.taxibooking_customer_api.repository.AdminRepository;
//import com.example.taxibooking_customer_api.service.AdminService;
//import com.example.taxibooking_customer_api.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//    @Autowired
//    AdminRepository adminRepository;
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    @Override
//    public boolean findByUsername(LoginDTO loginDTO){
//        Admin admin = adminRepository.findByUsername(loginDTO.getUsername());
//        return admin != null && admin.getPassword().equals(loginDTO.getPassword());
//    }
//
//    public ResponseEntity<?> login(LoginDTO loginDTO) {
//        Map<String, String> data  = new HashMap<>();
//        if(loginDTO.getUsername().equals("")) {
//            data.put("data","Please enter username");
//            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
//        }else if(loginDTO.getPassword().equals("")){
//            data.put("dat","Please enter password");
//            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
//        }else{
//            Admin admin = adminRepository.findOneByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
//            if(admin == null){
//                data.put("data","Invalid credentials");
//                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Login Failed");
//            }
//
//            String token =  jwtUtil.generateAccessToken(admin);
//            data.put("data","success");
//            data.put("userRole",admin.getRole());
//            data.put("accessToken",token);
//
//            return ResponseEntity.status(HttpStatus.OK).body(data);
//        }
//
//    }
//
//}
