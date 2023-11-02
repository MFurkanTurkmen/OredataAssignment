package com.oredata.assignment.config;

import com.oredata.assignment.dto.response.RoleResponse;
import com.oredata.assignment.exception.ErrorType;
import com.oredata.assignment.exception.OredataException;
import com.oredata.assignment.repository.entity.UserProfile;
import com.oredata.assignment.service.RoleService;
import com.oredata.assignment.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Configuration
public class JwtUserDetail implements UserDetailsService {
    @Autowired
    UserProfileService userProfileService;
    @Autowired
    RoleService roleService;

    public UserDetails getUserById(Long id){
        Optional<UserProfile> userProfile = userProfileService.findById(id);
        if(userProfile.isEmpty()) throw new OredataException(ErrorType.USER_NOT_FOUND);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleResponse role: roleService.getRolesByUserId(userProfile.get().getId()).getRoleResponses()){
         authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails user =User.builder()
                .username(userProfile.get().getEmail())
                .password("")
                .authorities(authorities)
                .build();
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
