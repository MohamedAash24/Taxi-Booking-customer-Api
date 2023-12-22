package com.example.taxibooking_customer_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_user")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_user_id")
    private int adminUserId;
    @Column(name = "email", columnDefinition = "TEXT")
    private String email;
    @Column(name = "password", columnDefinition = "TEXT")
    private String password;
    @Column(name = "username", length = 150)
    private String username;
    @Column(name = "role", length = 45)
    private String role;
    @Column(name = "system_date", length = 45)
    private String systemDate;
    @Column(name = "system_time", length = 45)
    private String systemTime;
    @Column(name = "deleted_admin_id")
    private int deletedAdminId=0;
    @Column(name = "deleted_admin")
    private String deletedAdmin;
    @Column(name = "deleted_date", length = 45)
    private String deletedDate;
    @Column(name = "deleted_time", length = 45)
    private String deletedTime;
    @Column(name = "status")
    private int status=1;

}
