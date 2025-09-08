package com.example.LoanEligibilityChecker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Table(name = "loan_applications")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrower_request_id", nullable = false)
    private BorrowerRequest borrowerRequest;

    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private LenderRules rules;

    private String status;

}
