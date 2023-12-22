package com.example.taxibooking_customer_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rider_contact")
public class RiderContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_contact_id")
    private int riderContactId;
    @Column(name = "rider_id")
    private int riderId;
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "message",columnDefinition = "TEXT")
    private String message;
    @Column(name = "date", length = 45)
    private String date;
    @Column(name = "time", length = 45)
    private String time;
    @Column(name = "status")
    private int status=1;
    @ManyToOne
    @JoinColumn(name = "rider_id", referencedColumnName = "rider_Id", updatable = false, insertable = false)
    private Rider rider;


}
