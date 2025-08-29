package com.example.LoanEligibilityChecker.Dto;

import lombok.Data;

@Data
public class LenderRequestDto {
    private String firstName;
    private String lastName;
    private String companyName;
    private String licenseNumber;
    private String phoneNumber;
    private String address;
}
