package com.example.LoanEligibilityChecker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lenders")
@Builder
@Data
public class Lender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String licenseNumber;
    private String phoneNumber;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
