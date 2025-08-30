package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.Lender;
import com.example.LoanEligibilityChecker.Entity.LenderRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LenderRulesRepository extends JpaRepository<LenderRules, Long> {

    Optional<LenderRules> findByLender(Lender existingLender);
    Optional<LenderRules> findByLenderId(Long lenderId);
}
