package com.example.taxibooking_customer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateRideDTO {

    private String appId;
    private String verification;
    private int customerId;
    private int tripId;
    private String starRate;
    private String comment;
}
