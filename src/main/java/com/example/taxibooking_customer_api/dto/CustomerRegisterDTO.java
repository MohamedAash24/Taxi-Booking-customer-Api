package com.example.taxibooking_customer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {

    private  String mobile;
    private  String name;
    private  String notificationKey;
    private  String currentLat;
    private  String currentLon;

}
