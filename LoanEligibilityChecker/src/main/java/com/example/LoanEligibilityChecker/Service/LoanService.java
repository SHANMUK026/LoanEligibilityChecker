package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.LenderResponseDto;
import com.example.LoanEligibilityChecker.Entity.Borrower;
import com.example.LoanEligibilityChecker.Entity.BorrowerRequest;
import com.example.LoanEligibilityChecker.Entity.Lender;
import com.example.LoanEligibilityChecker.Exception.ResourceNotFoundEx;
import com.example.LoanEligibilityChecker.Repository.BorrowerRepository;
import com.example.LoanEligibilityChecker.Repository.BorrowerRequestRepository;
import com.example.LoanEligibilityChecker.Repository.LenderRulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LenderRulesRepository lenderRulesRepository;
    private final BorrowerRequestRepository borrowerRequestRepository;
    private final BorrowerRepository borrowerRepository;

    private Borrower currentBorrower() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return borrowerRepository.findByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower not found with username: " + username));
    }

    public List<LenderResponseDto> getEligibleLenders(Double salary) {
        Borrower borrower = currentBorrower();

        BorrowerRequest request = borrowerRequestRepository.findByBorrower_Id(borrower.getId())
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower request not found for borrower id: " + borrower.getId()));

        return lenderRulesRepository.findEligibleLenderRules(
                        salary,
                        request.getLoanAmount(),
                        request.getCreditScore(),
                        request.getAge(),
                        request.getEmploymentStatus()
                )
                .stream()
                .map(rule -> LenderResponseDto.builder()
                        .id(rule.getLender().getId())
                        .name(rule.getLender().getCompanyName())
                        .interestRate(rule.getInterestRate())
                        .minimumLoanAmount(rule.getMinimumLoanAmount())
                        .maximumLoanAmount(rule.getMaximumLoanAmount())
                        .build())
                .toList();
    }

}
