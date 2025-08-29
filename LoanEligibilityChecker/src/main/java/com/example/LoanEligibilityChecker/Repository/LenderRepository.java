package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.Lender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LenderRepository extends JpaRepository<Lender, Long> {
    Optional<Lender> findByLicenseNumber(String licenseNumber);
    Optional<Lender> findByCompanyName(String companyName);
}
