package com.oredata.assignment.config;

import com.oredata.assignment.exception.ErrorType;
import com.oredata.assignment.exception.OredataException;
import com.oredata.assignment.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUserDetail jwtUserDetail;
    @Autowired
    RateLimit rateLimit;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeaderBearerToken = request.getHeader("Authorization");
        if(authHeaderBearerToken!=null && authHeaderBearerToken.startsWith("Bearer ")
                &&  SecurityContextHolder.getContext().getAuthentication() == null
        ){

            if (rateLimit.isRateLimitExceeded(request.getRemoteAddr())) {
                throw new OredataException(ErrorType.RATE_LIMIT);
        }else {

                String token = authHeaderBearerToken.substring(7);
            Optional<Long> id = jwtTokenManager.getIdFromToken(token);
            if (id.isEmpty()) throw new OredataException(ErrorType.USER_NOT_FOUND);

            UserDetails userDetails = jwtUserDetail.getUserById(id.get());
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        }


        filterChain.doFilter(request,response);
    }
}
