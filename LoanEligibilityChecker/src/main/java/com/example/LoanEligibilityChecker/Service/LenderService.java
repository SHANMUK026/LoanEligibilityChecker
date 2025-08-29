package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.LenderRequestDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Entity.Lender;
import com.example.LoanEligibilityChecker.Entity.User;
import com.example.LoanEligibilityChecker.Exception.ResourceAlreadyExistEx;
import com.example.LoanEligibilityChecker.Repository.LenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LenderService {

    private final LenderRepository lenderRepository;


    public void registerLender(LenderRequestDto lenderRequestDto, User user) {

        Optional<Lender> lenderByLicenseNumber = lenderRepository.findByLicenseNumber(lenderRequestDto.getLicenseNumber());
        Optional<Lender> lenderByCompanyName = lenderRepository.findByCompanyName(lenderRequestDto.getCompanyName());

        if (lenderByLicenseNumber.isPresent()){
            throw new ResourceAlreadyExistEx("Lender already exists with License Number" + lenderRequestDto.getLicenseNumber());
        }

        if (lenderByCompanyName.isPresent()) {
            throw new ResourceAlreadyExistEx("Lender already exists with company name " + lenderRequestDto.getCompanyName());
        }


        Lender lender = Lender.builder()
                .firstName(lenderRequestDto.getFirstName())
                .lastName(lenderRequestDto.getLastName())
                .companyName(lenderRequestDto.getCompanyName())
                .licenseNumber(lenderRequestDto.getLicenseNumber())
                .phoneNumber(lenderRequestDto.getPhoneNumber())
                .address(lenderRequestDto.getAddress())
                .user(user)
                .build();
        lenderRepository.save(lender);


    }

}
