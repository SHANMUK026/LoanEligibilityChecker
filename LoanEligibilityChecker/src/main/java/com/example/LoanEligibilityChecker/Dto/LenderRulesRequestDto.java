package com.example.LoanEligibilityChecker.Dto;

import lombok.Data;

@Data
public class LenderRulesRequestDto {
    private Double minimumSalary;
    private  Double minimumLoanAmount;
    private  Double maximumLoanAmount;
    private  Double interestRate;
    private int minimumCreditScore;
    private int minimumAge;
    private int maximumAge;
    private String employmentTypes;
    private String  ruleStatus;
}
