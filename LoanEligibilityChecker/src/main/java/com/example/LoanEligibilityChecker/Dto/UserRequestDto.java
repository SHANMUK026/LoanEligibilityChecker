package com.example.LoanEligibilityChecker.Dto;


import lombok.Data;

@Data
public class UserRequestDto {
    private String userName;
    private String email;
    private String password;
    private String role;
    private LenderRequestDto lender;
    private BorrowerRequestDto borrower;
}
