package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.LenderRulesRequestDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Entity.Lender;
import com.example.LoanEligibilityChecker.Entity.LenderRules;
import com.example.LoanEligibilityChecker.Exception.ResourceNotFoundEx;
import com.example.LoanEligibilityChecker.Repository.LenderRepository;
import com.example.LoanEligibilityChecker.Repository.LenderRulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class LenderRulesService {
    private final LenderRulesRepository lenderRulesRepository;
    private final LenderRepository lenderRepository;

    public Lender getCurrentLender() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return lenderRepository.findByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundEx("Lender not found with username: " + username));

    }

    public ResponseDto createLenderRules(LenderRulesRequestDto rulesRequestDto) {
        Lender existingLender=getCurrentLender();

        LenderRules lenderRules = LenderRules.builder()
                .minimumSalary(rulesRequestDto.getMinimumSalary())
                .minimumLoanAmount(rulesRequestDto.getMinimumLoanAmount())
                .maximumLoanAmount(rulesRequestDto.getMaximumLoanAmount())
                .interestRate(rulesRequestDto.getInterestRate())
                .minimumCreditScore(rulesRequestDto.getMinimumCreditScore())
                .minimumAge(rulesRequestDto.getMinimumAge())
                .maximumAge(rulesRequestDto.getMaximumAge())
                .employmentTypes(rulesRequestDto.getEmploymentTypes())
                .ruleStatus(rulesRequestDto.getRuleStatus())
                .lender(existingLender)
                .build();

       LenderRules savedRule= lenderRulesRepository.save(lenderRules);
       return new ResponseDto("Lender Rules created successfully with id: " + savedRule.getId());

    }
    public ResponseDto updateLenderRules(Long id,LenderRulesRequestDto rulesRequestDto) {
        Lender existingLender=getCurrentLender();

        LenderRules existingRules = lenderRulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Rules not found for lender: " + existingLender.getCompanyName()));

        if (!existingRules.getLender().equals(existingLender)) {
            throw new ResourceNotFoundEx("Rules with id: " + id + " does not belong to lender: " + existingLender.getCompanyName());
        }

        existingRules.setMinimumSalary(rulesRequestDto.getMinimumSalary());
        existingRules.setMinimumLoanAmount(rulesRequestDto.getMinimumLoanAmount());
        existingRules.setMaximumLoanAmount(rulesRequestDto.getMaximumLoanAmount());
        existingRules.setInterestRate(rulesRequestDto.getInterestRate());
        existingRules.setMinimumCreditScore(rulesRequestDto.getMinimumCreditScore());
        existingRules.setMinimumAge(rulesRequestDto.getMinimumAge());
        existingRules.setMaximumAge(rulesRequestDto.getMaximumAge());
        existingRules.setEmploymentTypes(rulesRequestDto.getEmploymentTypes());
        existingRules.setRuleStatus(rulesRequestDto.getRuleStatus());

        LenderRules updatedRules = lenderRulesRepository.save(existingRules);
        return new ResponseDto("Lender Rules updated successfully with id: " + updatedRules.getId());
    }

    public ResponseDto deleteLenderRules(Long id) {
        Lender existingLender=getCurrentLender();
        LenderRules existingRules = lenderRulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Rules not found with id: " + id));

        if (!existingRules.getLender().equals(existingLender)) {
            throw new ResourceNotFoundEx("Rules with id: " + id + " does not belong to lender: " + existingLender.getCompanyName());
        }

        lenderRulesRepository.delete(existingRules);
        return new ResponseDto("Lender Rules deleted successfully with id: " + id);
    }

    public ResponseDto getLenderRules(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Lender existingLender = lenderRepository.findByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundEx("Lender not found with username: " + username));

        LenderRules existingRules = lenderRulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Rules not found with id: " + id));

        if (!existingRules.getLender().equals(existingLender)) {
            throw new ResourceNotFoundEx("Rules with id: " + id + " does not belong to lender: " + existingLender.getCompanyName());
        }

        return new ResponseDto("Lender Rules retrieved successfully with id: " + existingRules.getId());
    }
}
