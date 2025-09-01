package com.example.LoanEligibilityChecker.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LenderResponseDto {
    private Long id;
    private String name;
    private Double interestRate;
    private Double minimumLoanAmount;
    private Double maximumLoanAmount;
}
