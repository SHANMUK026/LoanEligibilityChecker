package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.BorrowerRequestDto;
import com.example.LoanEligibilityChecker.Entity.Borrower;
import com.example.LoanEligibilityChecker.Entity.User;
import com.example.LoanEligibilityChecker.Repository.BorrowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowerService {
    private final BorrowerRepository borrowerRepository;

    public void registerBorrower(BorrowerRequestDto borrowerRequestDto, User user) {
        Borrower borrower = Borrower.builder()
                .firstName(borrowerRequestDto.getFirstName())
                .lastName(borrowerRequestDto.getLastName())
                .phoneNumber(borrowerRequestDto.getPhoneNumber())
                .address(borrowerRequestDto.getAddress())
                .user(user)
                .build();
        borrowerRepository.save(borrower);

    }

}
