package com.example.LoanEligibilityChecker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "borrower_requests")
public class BorrowerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double loanAmount;
    private String loanPurpose;
    private String employmentStatus;
    private int creditScore;
    private int age;

    @OneToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private Borrower borrower;

    @OneToMany(mappedBy = "borrowerRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanApplication> loanApplications = new ArrayList<>();
}
