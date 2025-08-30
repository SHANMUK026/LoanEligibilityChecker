package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.Borrower;
import com.example.LoanEligibilityChecker.Entity.Lender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Optional<Borrower> findByUser_UserName(String userName);

}
