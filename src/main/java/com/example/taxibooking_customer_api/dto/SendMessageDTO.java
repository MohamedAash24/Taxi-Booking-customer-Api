package com.example.taxibooking_customer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessageDTO {
    private String verification;
    private int customerId;
    private String  email;
    private String  message;
}
