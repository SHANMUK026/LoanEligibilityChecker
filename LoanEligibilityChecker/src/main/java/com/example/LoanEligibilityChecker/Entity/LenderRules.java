package com.example.LoanEligibilityChecker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "lender_rules")
public class LenderRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double minimumSalary;
    private  Double minimumLoanAmount;
    private  Double maximumLoanAmount;
    private  Double interestRate;
    private int minimumCreditScore;
    private int minimumAge;
    private int maximumAge;
    private String employmentTypes;
    private String  ruleStatus;

    @ManyToOne
    @JoinColumn(name = "lender_id", nullable = false)
    private Lender lender;


}
