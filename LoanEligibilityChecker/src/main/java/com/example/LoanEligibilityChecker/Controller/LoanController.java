package com.example.LoanEligibilityChecker.Controller;

import com.example.LoanEligibilityChecker.Dto.LenderResponseDto;
import com.example.LoanEligibilityChecker.Entity.BorrowerRequest;
import com.example.LoanEligibilityChecker.Entity.Lender;
import com.example.LoanEligibilityChecker.Service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply-loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;


    @GetMapping
    public ResponseEntity<List<LenderResponseDto>> applyLoan(@RequestParam Double salary) {
        List<LenderResponseDto> lenders = loanService.getEligibleLenders(salary);
        return ResponseEntity.ok(lenders);
    }
}
