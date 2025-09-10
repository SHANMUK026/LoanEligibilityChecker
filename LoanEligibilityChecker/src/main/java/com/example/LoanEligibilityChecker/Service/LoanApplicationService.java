package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.BorrowerLoanApplicationDto;
import com.example.LoanEligibilityChecker.Dto.LenderResponseDto;
import com.example.LoanEligibilityChecker.Dto.LoanApplicationResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Entity.*;
import com.example.LoanEligibilityChecker.Exception.ResourceNotFoundEx;
import com.example.LoanEligibilityChecker.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LenderRulesRepository lenderRulesRepository;
    private final BorrowerRequestRepository borrowerRequestRepository;
    private final BorrowerRepository borrowerRepository;
    private final LenderRepository lenderRepository;
    private final LenderRulesRepository lenderRulesRepo;
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


    public BorrowerLoanApplicationDto mapToBorrowerDto(LoanApplication loanApplication){
        BorrowerLoanApplicationDto response= new BorrowerLoanApplicationDto();

        response.setLoanApplicationId(loanApplication.getId());
        response.setBorrowerName(loanApplication.getBorrowerRequest().getBorrower().getFirstName());
        response.setStatus(loanApplication.getStatus());
        response.setLoanPurpose(loanApplication.getBorrowerRequest().getLoanPurpose());
        response.setLoanAmount(loanApplication.getBorrowerRequest().getLoanAmount());
        response.setInterestRate(loanApplication.getRules().getInterestRate());
        response.setLenderCompanyName(loanApplication.getRules().getLender().getCompanyName());
        return response;
    }



    public List<LenderResponseDto> getEligibleLenders(Long reqId) {

        BorrowerRequest request = borrowerRequestRepository.findById(reqId)
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower request not found for id: " + reqId));

        return lenderRulesRepository.findEligibleLenderRules(
                        request.getSalary(),
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

    public ResponseDto applyForLoan(Long reqId,Long ruleId){

        Optional<LoanApplication> application=loanApplicationRepository.findByBorrowerRequest_IdAndRules_Id(reqId, ruleId);

        if(application.isPresent()){
            throw new ResourceNotFoundEx("You have already applied for this loan");
        }

        BorrowerRequest request = borrowerRequestRepository.findById(reqId)
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower request not found for id: " + reqId));

        LenderRules rule=lenderRulesRepository.findById(ruleId)
                .orElseThrow(() -> new ResourceNotFoundEx("Rule not found with id: " + ruleId));


        LoanApplication loanApplication= LoanApplication.builder()
                .borrowerRequest(request)
                .rules(rule)
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
        return loanApplicationRepository.findByRules_Lender_Id(lender.getId())
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public ResponseDto updateLoanApplicationStatus(Long id, String status){
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Loan application not found with id: " + id));

        if ("APPROVED".equalsIgnoreCase(status)) {
            Long requestId = loanApplication.getBorrowerRequest().getId();

            List<LoanApplication> loanApplications =
                    loanApplicationRepository.findByBorrowerRequest_Id(requestId);

            for (LoanApplication app : loanApplications) {
                if (!app.getId().equals(loanApplication.getId())) {
                    app.setStatus("REJECTED");
                }
            }
            loanApplicationRepository.saveAll(loanApplications);
        }

        loanApplication.setStatus(status);
        loanApplicationRepository.save(loanApplication);
        return new ResponseDto("Loan application status updated successfully with id: " + id);
    }

    public List<BorrowerLoanApplicationDto> getBorrowerLoanApplication(){
        return loanApplicationRepository.findByBorrowerRequest_Borrower_Id(currentBorrower().getId())
                .stream()
                .map(this::mapToBorrowerDto)
                .toList();

    }
}
