package com.example.LoanEligibilityChecker.Dto;

import lombok.Data;

@Data
public class BorrowerReqRequestDto {
    private Double loanAmount;
    private String loanPurpose;
    private String employmentStatus;
    private int creditScore;
    private double salary;
    private int age;
}
