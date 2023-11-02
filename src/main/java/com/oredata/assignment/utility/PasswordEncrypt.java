package com.oredata.assignment.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Configuration
public class PasswordEncrypt {
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder (){
         return new BCryptPasswordEncoder();
     }


    public String passwordHash(String password){

        String passwordHashed= bCryptPasswordEncoder().encode(password);
        return passwordHashed;
    }
    public boolean checkPassword(String password,String hashedPassword ){
        boolean match= bCryptPasswordEncoder().matches(password,hashedPassword);
        return match;
    }


}
