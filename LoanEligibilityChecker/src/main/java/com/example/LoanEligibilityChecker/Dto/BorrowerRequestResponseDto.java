package com.example.LoanEligibilityChecker.Dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BorrowerRequestResponseDto {
    private Long id;
    private Double loanAmount;
    private String loanPurpose;
    private String employmentStatus;
    private int creditScore;
    private int age;
}
