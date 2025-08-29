package com.example.LoanEligibilityChecker.Exception;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials(String message) {
        super(message);
    }
}
