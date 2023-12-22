package com.example.taxibooking_customer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestTripHistoryDTO {

    private String verification;
    private int customerId;
    private String date;
}
