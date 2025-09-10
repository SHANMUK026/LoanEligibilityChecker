package com.example.LoanEligibilityChecker.Repository;

import com.example.LoanEligibilityChecker.Entity.Lender;
import com.example.LoanEligibilityChecker.Entity.LenderRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LenderRulesRepository extends JpaRepository<LenderRules, Long> {

    @Query("SELECT lr FROM LenderRules lr " +
            "WHERE :salary >= lr.minimumSalary " +
            "AND :loanAmount BETWEEN lr.minimumLoanAmount AND lr.maximumLoanAmount " +
            "AND :creditScore >= lr.minimumCreditScore " +
            "AND :age BETWEEN lr.minimumAge AND lr.maximumAge " +
            "AND LOWER(lr.employmentTypes) LIKE CONCAT('%', LOWER(:employmentStatus), '%') " +
            "AND LOWER(lr.ruleStatus) = 'active'")
    List<LenderRules> findEligibleLenderRules(@Param("salary") Double salary,
                                              @Param("loanAmount") Double loanAmount,
                                              @Param("creditScore") int creditScore,
                                              @Param("age") int age,
                                              @Param("employmentStatus") String employmentStatus);

    // Fix for LenderRulesService dependency
    List<LenderRules> findAllByLender(Lender lender);
}
