package com.oredata.assignment.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;
@Configuration
public class JwtTokenManager {

    @Value("${passkey}")
    private String passKey;
    private final Long exTime = 1000L * 60 * 60 * 5; // 5 hours

    public Optional<String> createToken(Long id) {
        String token = "";
        try {

            token = JWT.create().withAudience()
                    .withClaim("id", id)
                    .withIssuer("muhammed_furkan_turkmen")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + exTime))
                    .sign(Algorithm.HMAC512(passKey));
            return Optional.of(token);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }



    public Optional<Long> getIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(passKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("muhammed_furkan_turkmen").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT==null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        }catch (Exception exception){
            return Optional.empty();
        }
    }

}
