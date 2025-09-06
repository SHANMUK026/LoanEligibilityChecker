package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.LenderResponseDto;
import com.example.LoanEligibilityChecker.Dto.LoanApplicationResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Entity.*;
import com.example.LoanEligibilityChecker.Exception.ResourceNotFoundEx;
import com.example.LoanEligibilityChecker.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LenderRulesRepository lenderRulesRepository;
    private final BorrowerRequestRepository borrowerRequestRepository;
    private final BorrowerRepository borrowerRepository;
    private final LenderRepository lenderRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    private Borrower currentBorrower() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return borrowerRepository.findByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower not found with username: " + username));
    }

    public LoanApplicationResponseDto mapToDto(LoanApplication loanApplication){
        LoanApplicationResponseDto dto = LoanApplicationResponseDto.builder()
                .age(loanApplication.getBorrowerRequest().getAge())
                .creditScore(loanApplication.getBorrowerRequest().getCreditScore())
                .employmentStatus(loanApplication.getBorrowerRequest().getEmploymentStatus())
                .loanAmount(loanApplication.getBorrowerRequest().getLoanAmount())
                .loanApplicationId(loanApplication.getId())
                .loanPurpose(loanApplication.getBorrowerRequest().getLoanPurpose())
                .borrowerName(loanApplication.getBorrowerRequest().getBorrower().getFirstName())
                .status(loanApplication.getStatus())
                .build();

        return dto;

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
                        .lenderId(rule.getLender().getId())
                        .ruleId(rule.getId())
                        .name(rule.getLender().getCompanyName())
                        .interestRate(rule.getInterestRate())
                        .minimumLoanAmount(rule.getMinimumLoanAmount())
                        .maximumLoanAmount(rule.getMaximumLoanAmount())
                        .build())
                .toList();
    }

    public ResponseDto applyForLoan(Long lenderId){
        Borrower borrower = currentBorrower();
        BorrowerRequest request = borrowerRequestRepository.findByBorrower_Id(borrower.getId())
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower request not found for borrower id: " + borrower.getId()));

        Lender lender = lenderRepository.findById(lenderId)
                .orElseThrow(() -> new ResourceNotFoundEx("Lender not found with id: " + lenderId));

        LoanApplication loanApplication= LoanApplication.builder()
                .borrowerRequest(request)
                .lender(lender)
                .status("PENDING")
                .build();

        LoanApplication appliedLoanApplication= loanApplicationRepository.save(loanApplication);
        return new ResponseDto("Loan application created successfully with id: " + appliedLoanApplication.getId());
    }

    private Lender currentLender() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return lenderRepository.findByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundEx("Lender not found with username: " + username));
    }

    public List<LoanApplicationResponseDto> getBorrowerApplications() {
        Lender lender = currentLender();
        return loanApplicationRepository.findByLender_Id(lender.getId())
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public ResponseDto updateLoanApplicationStatus(Long id, String status){
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Loan application not found with id: " + id));
        loanApplication.setStatus(status);
        loanApplicationRepository.save(loanApplication);
        return new ResponseDto("Loan application status updated successfully with id: " + id);
    }

    public LoanApplicationResponseDto getBorrowerLoanApplication(){
        LoanApplication loanApplication=loanApplicationRepository.findByBorrowerRequest_Borrower_Id(currentBorrower().getId())
                .orElseThrow(() -> new ResourceNotFoundEx("Loan application not found for borrower id: " + currentBorrower().getId()));
        return mapToDto(loanApplication);
    }
}
