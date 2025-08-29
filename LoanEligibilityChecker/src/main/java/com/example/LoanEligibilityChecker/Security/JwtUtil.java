package com.example.LoanEligibilityChecker.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String JWT_SECRET = "secretsecretsecretsecretsecretsecretsecret";
    private final Long JWT_EXPIRATION_MS = 10L*24*60*60*1000;


    public String generateJwtToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public Claims getClaimsFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserNameFromJwtToken(String token){
        return getClaimsFromJwtToken(token).getSubject();
    }

    public boolean validateJwtToken(String token){
        try{
            getClaimsFromJwtToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
