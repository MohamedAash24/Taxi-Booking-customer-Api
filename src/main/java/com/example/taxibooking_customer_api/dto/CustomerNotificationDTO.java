package com.example.taxibooking_customer_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNotificationDTO {

    private  int customerId;
    private  String notification;
    private String notificationTopic;
    private  int showToUser;
}
