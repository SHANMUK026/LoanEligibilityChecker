package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.BorrowerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRequestRepository extends JpaRepository<BorrowerRequest, Long> {

}
