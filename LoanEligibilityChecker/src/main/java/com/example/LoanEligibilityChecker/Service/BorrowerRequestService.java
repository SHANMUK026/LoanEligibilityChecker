package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Repository.BorrowerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowerRequestService {
    private final BorrowerRequestRepository borrowerRequestRepository;

}
