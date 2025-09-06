package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository <LoanApplication, Long>{
    List<LoanApplication> findByLender_Id(Long id);
    Optional<LoanApplication> findByBorrowerRequest_Borrower_Id(Long id);
}
