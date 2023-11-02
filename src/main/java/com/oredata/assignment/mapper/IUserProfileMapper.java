package com.oredata.assignment.mapper;

import com.oredata.assignment.dto.request.SignUpRequest;
import com.oredata.assignment.dto.request.UserUpdateRequest;
import com.oredata.assignment.dto.response.UserProfileResponse;
import com.oredata.assignment.repository.entity.UserProfile;

public interface IUserProfileMapper {
    UserProfile toUserProfile(SignUpRequest dto,String hashpass);
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
    UserProfile toUserProfile(UserUpdateRequest dto,UserProfile userProfile);
}
