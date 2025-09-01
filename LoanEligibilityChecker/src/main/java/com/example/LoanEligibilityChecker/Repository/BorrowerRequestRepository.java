package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.BorrowerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRequestRepository extends JpaRepository<BorrowerRequest, Long> {
    Optional<BorrowerRequest> findByBorrower_User_UserName(String userName);
    Optional<BorrowerRequest> findByBorrower_Id(Long id);

}
