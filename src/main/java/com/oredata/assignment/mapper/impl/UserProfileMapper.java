package com.oredata.assignment.mapper.impl;

import com.oredata.assignment.dto.request.SignUpRequest;
import com.oredata.assignment.dto.request.UserUpdateRequest;
import com.oredata.assignment.dto.response.UserProfileResponse;
import com.oredata.assignment.mapper.IUserProfileMapper;
import com.oredata.assignment.repository.entity.UserProfile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper implements IUserProfileMapper {

    @Override
    public UserProfile toUserProfile(SignUpRequest dto,String hashpass) {
        if (dto == null) return null;
        return UserProfile.builder()
                .password(hashpass)
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public UserProfileResponse toUserProfileResponse(UserProfile userProfile) {
        if (userProfile==null) return null;
        return UserProfileResponse.builder()
                .name(userProfile.getName())
                .email(userProfile.getEmail())
                .message("ok")
                .statusCode(200)
                .build();
    }

    @Override
    public UserProfile toUserProfile(UserUpdateRequest dto,UserProfile userProfile) {
        if (dto==null) return null;
        if (dto.getEmail()!=null && !dto.getEmail().isBlank())
        userProfile.setEmail(dto.getEmail());
        if (dto.getName()!=null && !dto.getName().isBlank())
        userProfile.setName(dto.getName());
        return userProfile;
    }

}
