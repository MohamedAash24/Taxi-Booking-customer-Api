package com.example.taxibooking_customer_api.service.serviceImpl;

import com.example.taxibooking_customer_api.dto.*;
import com.example.taxibooking_customer_api.entity.Customer;
import com.example.taxibooking_customer_api.repository.CustomerRepository;
import com.example.taxibooking_customer_api.repository.UpdateAdderssRepositorey;
import com.example.taxibooking_customer_api.service.CustomerNotificationService;
import com.example.taxibooking_customer_api.service.CustomerService;
import com.example.taxibooking_customer_api.util.JwtUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerNotificationService customerNotificationService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;

    @Autowired
    UpdateAdderssRepositorey updateAdderssRepositorey;

    public  static  final String UPLOAD_DRT = "upload";

    @Override
    public ResponseEntity<?>customer_register (CustomerRegisterDTO customerRegisterDTO) {

            if (customerRegisterDTO.getMobile().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile not available");
            } else if (customerRegisterDTO.getName().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Name not available");
            } else if (customerRegisterDTO.getNotificationKey().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification key not available");
            } else if (customerRegisterDTO.getCurrentLat().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current Lat not available");
            } else if (customerRegisterDTO.getCurrentLon().equals("")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Current Lon not available");
            } else {
                Optional<Customer> optionalCustomer = customerRepository.findByMobile1(customerRegisterDTO.getMobile());
                if (optionalCustomer.isEmpty()) {
                    Customer customer = new Customer();
                    customer.setMobile1(customerRegisterDTO.getMobile());
                    customer.setUsername(customerRegisterDTO.getName());
                    customer.setNotificationKey(customerRegisterDTO.getNotificationKey());
                    customer.setCurrentLat(customerRegisterDTO.getCurrentLat());
                    customer.setCurrentLon(customerRegisterDTO.getCurrentLon());

                    customerRepository.save(customer);

                    customerNotificationService.saveNotification(new CustomerNotificationDTO(
                            customerRepository.findByMobile1(customerRegisterDTO.getMobile()).get().getCustomerId(), "Successful Account Creation",
                            "Registering as a New Customer",
                            0));


                } else if (String.valueOf(optionalCustomer.get().getStatus()).equals("0")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You Account BlackList. Cannot Login To Account");

                }

                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        customerRepository.findByMobile1(customerRegisterDTO.getMobile()).get().getCustomerId(),
                        "Success Login",
                        "Login Customer",
                        1));

                return ResponseEntity.status(HttpStatus.OK).body("Success");
            }
        }

    @Override
    public ResponseEntity<?> getCustomerPinNumber(CustomerMobileDTO customerMobileDTO){
        if (customerMobileDTO.equals("")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile not available");
        }else {
            Optional<Customer> optionalCustomer = customerRepository.findByMobile1(customerMobileDTO.getMobile());
            if (optionalCustomer.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not available");
            }else {
                String pinNumber = String.format("%06d", new Random().nextInt(999999));
                Customer customer = optionalCustomer.get();
                customer.setVerification(pinNumber);
                customer.setLastOtpDate(String.valueOf(LocalDate.now()));
                customer.setLastOtpTime(String.valueOf(LocalTime.now()));
                customerRepository.save(customer);
                return  ResponseEntity.status(HttpStatus.OK).body(pinNumber);
            }
        }
    }
    @Override
    public  ResponseEntity<?> verifyCustomerMobile(VerifyRequestDTO verifyRequestDTO){
        if (verifyRequestDTO.getMobile().equals("")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile not available");
        }else if(verifyRequestDTO.getOtp().equals("")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP not available");
        }
        else {
            Optional<Customer> optionalCustomer = customerRepository.findByMobile1(verifyRequestDTO.getMobile());
            if (optionalCustomer.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile not available. Register Again");
            }else {
                Customer customer = optionalCustomer.get();
                if (!customer.getVerification().equals(verifyRequestDTO.getOtp())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is Wrong.Try Again");
                }
                customer.setStatus(1);
                customerRepository.save(customer);

                customerNotificationService.saveNotification(new CustomerNotificationDTO(
                        customerRepository.findByMobile1(verifyRequestDTO.getMobile()).get().getCustomerId(),
                        "Verification key",
                        "Verification send to your Phone number",
                        1));

                String accessToken = jwtUtil.generateAccessToken(customer);
                Map<String, String> data = new HashMap<>();
                data.put( "message","Good to go");
                data.put("accessToken", accessToken);
                return  ResponseEntity.status(HttpStatus.OK).body(data);
            }
        }
    }

    @Override
    public  ResponseEntity<?> updateProfile(CustomerUpdateProfileDTO customerUpdateProfileDTO){
        if(customerUpdateProfileDTO.getVerification().equals("")){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verify not available");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId()).get().getVerification().equals(customerUpdateProfileDTO.getVerification())) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verify not available");
        } else {

            Customer customer = customerRepository.findById(requestMetaDTO.getCustomerId()).get();
            customer.setFullName(customerUpdateProfileDTO.getFullName());
            customer.setMobile2(customerUpdateProfileDTO.getEmergencyNumber());
            customer.setBirthday(customerUpdateProfileDTO.getBirthday());
            customer.setEmail(customerUpdateProfileDTO.getEmail());
            customer.setNic(customerUpdateProfileDTO.getNic());

            customerRepository.save(customer);

            customerNotificationService.saveNotification(new CustomerNotificationDTO(
                    customerRepository.findByMobile1(requestMetaDTO.getMobile()).get().getCustomerId(),
                    "Profile update successfully",
                    "Profile Details update successfully",
                    1));

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }

    @Override
    public  ResponseEntity<?> updateHomeOfficeAddress(UpdateAddressDTO updateAddressDTO){
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id Not Available");
        } else if (updateAddressDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(updateAddressDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        }else if (updateAddressDTO.getAddressText().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address Text Not Available");
        }else if (updateAddressDTO.getAddressLat().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddressLat Not Available");
        }else if (updateAddressDTO.getAddressLon().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddressLon Not Available");
        } else if (String.valueOf(updateAddressDTO.getAddressType()).equals("0")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddressLat Type Not Available");
        }else{

            Customer customer = customerRepository.findById(requestMetaDTO.getCustomerId()).get();
            if (String.valueOf(updateAddressDTO.getAddressType()).equals("1")){
                customer.setHomeAddress(updateAddressDTO.getAddressText());
                customer.setHomeAddressLat(updateAddressDTO.getAddressLat());
                customer.setHomeAddressLon(updateAddressDTO.getAddressLon());
            }else if (String.valueOf(updateAddressDTO.getAddressType()).equals("2")){
                customer.setOfficeAddress(updateAddressDTO.getAddressText());
                customer.setOfficeAddressLat(updateAddressDTO.getAddressLat());
                customer.setOfficeAddressLon(updateAddressDTO.getAddressLon());
            }

            customerRepository.save(customer);

            customerNotificationService.saveNotification(new CustomerNotificationDTO(
                    customerRepository.findByMobile1(requestMetaDTO.getMobile()).get().getCustomerId(),
                    "Address update successfully",
                    "Address Details update successfully",
                    1));
            return ResponseEntity.status(HttpStatus.OK).body("Success!");
        }

    }

    @Override
    public  ResponseEntity<?> removeHomeOfficeAddress(RemoveAddressDTO removeAddressDTO){
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id Not Available");
        } else if (removeAddressDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(removeAddressDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        } else if (String.valueOf(removeAddressDTO.getAddressType()).equals("0")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddressLat Type Not Available");
        } else {
            Customer customer = customerRepository.findById(requestMetaDTO.getCustomerId()).get();
            if (String.valueOf(removeAddressDTO.getAddressType()).equals("1")){
                customer.setHomeAddress("");
                customer.setHomeAddressLat("");
                customer.setHomeAddressLon("");
                customer.setAddressType(0);
            }else if (String.valueOf(removeAddressDTO.getAddressType()).equals("2")){
                customer.setOfficeAddress("");
                customer.setOfficeAddressLat("");
                customer.setOfficeAddressLon("");
                customer.setAddressType(0);
            }
            customerRepository.save(customer);

            customerNotificationService.saveNotification(new CustomerNotificationDTO(
                    customerRepository.findByMobile1(requestMetaDTO.getMobile()).get().getCustomerId(),
                    "HomeOfficeAddress update successfully",
                    "HomeOfficeAddress Details update successfully",
                    1));
            return ResponseEntity.status(HttpStatus.OK).body("Success!");
        }
    }

    @Override
    public  ResponseEntity<?> updateProfileImage(String verification, MultipartFile multipartFile) throws IOException {
        if (customerRepository.findById(requestMetaDTO.getCustomerId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Id Not Available");
        } else if (verification.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not found");
        } else if (!customerRepository.findById(requestMetaDTO.getCustomerId())
                .get().getVerification().equals(verification)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("In Valid Verification");
        } else if (multipartFile.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Select a Image");
        } else {

            Path path = Paths.get(UPLOAD_DRT);

            if(!Files.exists(path)){
                 Files.createDirectory(path);
            }

            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String fileName = System.currentTimeMillis()+ "." + extension;

            Path filePath = path.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String appUrl = String.format("http://%s:%s", InetAddress.getLocalHost(), 8080);
            String url = UPLOAD_DRT + "/" + fileName;
            String fullUrl = appUrl + "/" + url;

            Customer customer = customerRepository.findById(requestMetaDTO.getCustomerId()).get();
            customer.setProfileImage(url);

            customerRepository.save(customer);

            return  ResponseEntity.status(HttpStatus.OK).body("Success!");

        }
    }
}
