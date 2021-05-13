package com.sms.invoicify.controller;


import com.sms.invoicify.models.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    List<Company> companyList = new ArrayList<>();
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getCompanyDetails(){
        return companyList;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String postCompanyDetails(@RequestBody Company company){
        companyList.add(company);
        return company.getCompanyName() + " created Successfully";
    }


}