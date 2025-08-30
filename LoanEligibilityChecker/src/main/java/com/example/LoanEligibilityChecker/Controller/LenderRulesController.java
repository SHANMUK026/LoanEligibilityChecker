package com.example.LoanEligibilityChecker.Controller;

import com.example.LoanEligibilityChecker.Dto.LenderRulesRequestDto;
import com.example.LoanEligibilityChecker.Dto.LenderRulesResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Service.LenderRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lender_rules")
@RequiredArgsConstructor
public class LenderRulesController {
    private final LenderRulesService lenderRulesService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLenderRules(@RequestBody LenderRulesRequestDto rulesRequestDto) {
        ResponseDto responseDto = lenderRulesService.createLenderRules(rulesRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateLenderRules(@PathVariable Long id,@RequestBody LenderRulesRequestDto rulesRequestDto) {
        ResponseDto responseDto = lenderRulesService.updateLenderRules(id,rulesRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LenderRulesResponseDto> getLenderRules(@PathVariable Long id) {
        LenderRulesResponseDto responseDto = lenderRulesService.getLenderRules(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLenderRules(@PathVariable Long id) {
        ResponseDto responseDto = lenderRulesService.deleteLenderRules(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<LenderRulesResponseDto> getAllRules() {
        return lenderRulesService.getAllLenderRules();
    }

        @GetMapping("/lender/{lenderId}")
        public List<LenderRulesResponseDto> getRulesByLender(@PathVariable Long lenderId) {
            return lenderRulesService.getAllRulesByLenderId(lenderId);
        }
    }



