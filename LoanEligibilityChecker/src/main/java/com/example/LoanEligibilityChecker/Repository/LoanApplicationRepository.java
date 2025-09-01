package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository <LoanApplication, Long>{
    List<LoanApplication> findByLender_IdAndStatus(Long id, String status);
}
