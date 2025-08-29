package com.example.LoanEligibilityChecker.Dto;

import lombok.Data;

@Data
public class BorrowerRequestDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}
