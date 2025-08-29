package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

}
