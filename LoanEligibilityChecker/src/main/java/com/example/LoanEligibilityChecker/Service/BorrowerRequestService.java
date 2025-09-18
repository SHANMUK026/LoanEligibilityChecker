package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.BorrowerReqRequestDto;
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

import java.util.List;

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
        borrowerRequestResponseDto.setSalary(borrowerRequest.getSalary());
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
                .borrower(existingBorrower)
                .fulfilled(false)
                .salary(borrowerRequestDto.getSalary())
                .build();

        BorrowerRequest savedRequest=borrowerRequestRepository.save(borrowerRequest);
        return new ResponseDto("Borrower request created successfully with id: " + savedRequest.getId());
    }
    public List<BorrowerRequestResponseDto> getBorrowerRequest(){
        Borrower existingBorrower=currentBorrower();

        return  borrowerRequestRepository.findByBorrower_User_UserName(existingBorrower.getUser().getUserName())
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
