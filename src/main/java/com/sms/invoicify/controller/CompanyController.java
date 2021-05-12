package com.sms.invoicify.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllItems(){
        return List.of();

    }


}