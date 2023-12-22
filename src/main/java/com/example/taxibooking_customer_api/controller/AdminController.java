package com.example.taxibooking_customer_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(){
        return "login";}

    @GetMapping("/rider")
    public String rider(){
        return "rider";}

    @GetMapping("/index")
    public String index(){
        return "index";}
}
