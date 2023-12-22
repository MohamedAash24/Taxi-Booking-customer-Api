package com.example.taxibooking_customer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestMetaDTO {
    private int customerId;
    private String name;
    private String mobile;

}
