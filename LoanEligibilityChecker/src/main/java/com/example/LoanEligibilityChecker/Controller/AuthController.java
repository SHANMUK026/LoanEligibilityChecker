package com.example.LoanEligibilityChecker.Controller;

import com.example.LoanEligibilityChecker.Dto.AuthRequestDto;
import com.example.LoanEligibilityChecker.Dto.AuthResponseDto;
import com.example.LoanEligibilityChecker.Dto.ResponseDto;
import com.example.LoanEligibilityChecker.Dto.UserRequestDto;
import com.example.LoanEligibilityChecker.Repository.UserRepository;
import com.example.LoanEligibilityChecker.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.registerUser(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody AuthRequestDto requestDto){
        return ResponseEntity.ok(userService.loginUser(requestDto));
    }
}
