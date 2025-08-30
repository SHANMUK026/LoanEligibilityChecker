package com.example.LoanEligibilityChecker.Controller;

import com.example.LoanEligibilityChecker.Dto.BorrowerReqRequestDto;
import com.example.LoanEligibilityChecker.Dto.BorrowerRequestResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Service.BorrowerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrower_request")
@RequiredArgsConstructor
public class BorrowerRequestController {
    private final BorrowerRequestService borrowerRequestService;

    @PostMapping
    public ResponseEntity<ResponseDto> createBorrowerRequest(@RequestBody BorrowerReqRequestDto requestDto){
        return new ResponseEntity<>(borrowerRequestService.createBorrowerRequest(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BorrowerRequestResponseDto> getBorrowerRequest(){
        return new ResponseEntity<>(borrowerRequestService.getBorrowerRequest(), HttpStatus.OK);
    }



}
