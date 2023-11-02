package com.oredata.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }
    private final String[] WHITELIST = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/user/signup",
            "/user/login",
            "/user/{id}",
            "/book/{isbn}",
            "/book/"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .anyRequest() .authenticated()
                .and().httpBasic();
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }

}
