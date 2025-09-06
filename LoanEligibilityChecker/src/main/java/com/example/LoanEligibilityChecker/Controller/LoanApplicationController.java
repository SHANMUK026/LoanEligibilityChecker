package com.example.LoanEligibilityChecker.Controller;

import com.example.LoanEligibilityChecker.Dto.LenderResponseDto;
import com.example.LoanEligibilityChecker.Dto.LoanApplicationResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanService;



    @GetMapping("/eligible-lenders")
    public ResponseEntity<List<LenderResponseDto>> getEligibleLoans(@RequestParam Double salary) {
            List<LenderResponseDto> lenders = loanService.getEligibleLenders(salary);
        return ResponseEntity.ok(lenders);
    }

    @PostMapping("/apply/{lenderId}")
    public ResponseEntity<ResponseDto> applyLoan(@PathVariable Long lenderId){
        return new ResponseEntity<>(loanService.applyForLoan(lenderId), HttpStatus.CREATED);
    }

    @GetMapping("/borrower-applications")
    public ResponseEntity<List<LoanApplicationResponseDto>> getLenderApplications() {
        return new  ResponseEntity<>(loanService.getBorrowerApplications(), HttpStatus.OK);
    }

    @PutMapping("/update-status/{applId}")
    public ResponseEntity<ResponseDto> updateApplicationStatus(@PathVariable Long applId, @RequestParam String status){
        return new ResponseEntity<>(loanService.updateLoanApplicationStatus(applId, status), HttpStatus.OK);
    }

    @GetMapping("/borrower/my-applications")
    public ResponseEntity<LoanApplicationResponseDto> getBorrowerLoanApplication(){
        return new ResponseEntity<>(loanService.getBorrowerLoanApplication(), HttpStatus.OK);
    }

}
