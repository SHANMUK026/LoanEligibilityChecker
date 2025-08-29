package com.example.LoanEligibilityChecker.Service;

import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Dto.UserRequestDto;
import com.example.LoanEligibilityChecker.Entity.User;
import com.example.LoanEligibilityChecker.Exception.ResourceAlreadyExistEx;
import com.example.LoanEligibilityChecker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BorrowerService borrowerService;
    private final LenderService lenderService;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto registerUser(UserRequestDto userRequestDto) {
        Optional<User> userByName = userRepository.findByUserName(userRequestDto.getUserName());
        Optional<User> userByEmail= userRepository.findByEmail(userRequestDto.getEmail());

        if (userByName.isPresent()) {
            throw new ResourceAlreadyExistEx("User already exists with username " + userRequestDto.getUserName());
        }

        if (userByEmail.isPresent()) {
            throw new ResourceAlreadyExistEx("User already exists with email " + userRequestDto.getEmail());
        }

        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole())
                .build();

        User savedUser=userRepository.save(user);

        if (savedUser.getRole().equals("BORROWER")) {
            borrowerService.registerBorrower(userRequestDto.getBorrower(),savedUser);
        }else if (savedUser.getRole().equals("LENDER")) {
            lenderService.registerLender(userRequestDto.getLender(),savedUser);
        }
        return new ResponseDto("User registered successfully");
    }

}
