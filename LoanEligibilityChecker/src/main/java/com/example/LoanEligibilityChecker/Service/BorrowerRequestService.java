package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.BorrowerReqRequestDto;
import com.example.LoanEligibilityChecker.Dto.BorrowerRequestDto;
import com.example.LoanEligibilityChecker.Dto.BorrowerRequestResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Entity.Borrower;
import com.example.LoanEligibilityChecker.Entity.BorrowerRequest;
import com.example.LoanEligibilityChecker.Exception.ResourceNotFoundEx;
import com.example.LoanEligibilityChecker.Repository.BorrowerRepository;
import com.example.LoanEligibilityChecker.Repository.BorrowerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowerRequestService {
    private final BorrowerRequestRepository borrowerRequestRepository;
    private final BorrowerRepository borrowerRepository;

    private Borrower currentBorrower(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return borrowerRepository.findByUser_UserName(username)
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower not found with username: " + username));

    };

    public BorrowerRequestResponseDto mapToDto(BorrowerRequest borrowerRequest){
        BorrowerRequestResponseDto borrowerRequestResponseDto=new BorrowerRequestResponseDto();
        borrowerRequestResponseDto.setAge(borrowerRequest.getAge());
        borrowerRequestResponseDto.setCreditScore(borrowerRequest.getCreditScore());
        borrowerRequestResponseDto.setEmploymentStatus(borrowerRequest.getEmploymentStatus());
        borrowerRequestResponseDto.setLoanAmount(borrowerRequest.getLoanAmount());
        borrowerRequestResponseDto.setLoanPurpose(borrowerRequest.getLoanPurpose());
        borrowerRequestResponseDto.setId(borrowerRequest.getId());
        return borrowerRequestResponseDto;
    }


    public ResponseDto createBorrowerRequest(BorrowerReqRequestDto borrowerRequestDto){
        Borrower existingBorrower=currentBorrower();

        BorrowerRequest borrowerRequest = BorrowerRequest.builder()
                .age(borrowerRequestDto.getAge())
                .loanAmount(borrowerRequestDto.getLoanAmount())
                .loanPurpose(borrowerRequestDto.getLoanPurpose())
                .creditScore(borrowerRequestDto.getCreditScore())
                .employmentStatus(borrowerRequestDto.getEmploymentStatus())
                .borrower(existingBorrower).build();

        BorrowerRequest savedRequest=borrowerRequestRepository.save(borrowerRequest);
        return new ResponseDto("Borrower request created successfully with id: " + savedRequest.getId());
    }
    public BorrowerRequestResponseDto getBorrowerRequest(){
        Borrower existingBorrower=currentBorrower();

        BorrowerRequest savedBorrowerReq= borrowerRequestRepository.findByBorrower_User_UserName(existingBorrower.getUser().getUserName())
                .orElseThrow(() -> new ResourceNotFoundEx("Borrower request not found for borrower: " + existingBorrower.getUser().getUserName()));

        return mapToDto(savedBorrowerReq);
    }
}
