package com.example.LoanEligibilityChecker.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationResponseDto {
    private Long loanApplicationId;
    private String loanPurpose;
    private String borrowerName;
    private String status;
    private Double loanAmount;
    private Integer creditScore;
    private Integer age;
    private String employmentStatus;
}
