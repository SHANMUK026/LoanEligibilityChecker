package com.example.LoanEligibilityChecker.Dto;

import lombok.Data;

@Data
public class BorrowerLoanApplicationDto {
    private Long loanApplicationId;
    private String loanPurpose;
    private String borrowerName;
    private String status;
    private Double loanAmount;
    private String lenderCompanyName;
    private Double interestRate;
}
