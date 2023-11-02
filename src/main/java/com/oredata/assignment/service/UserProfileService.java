package com.oredata.assignment.service;

import com.oredata.assignment.dto.request.LoginRequest;
import com.oredata.assignment.dto.request.SignUpRequest;
import com.oredata.assignment.dto.request.UserUpdateRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.LoginResponse;
import com.oredata.assignment.dto.response.UserProfileResponse;
import com.oredata.assignment.exception.ErrorType;
import com.oredata.assignment.exception.OredataException;
import com.oredata.assignment.mapper.IUserProfileMapper;
import com.oredata.assignment.repository.UserProfileRepository;
import com.oredata.assignment.repository.entity.Role;
import com.oredata.assignment.repository.entity.UserProfile;
import com.oredata.assignment.utility.JwtTokenManager;
import com.oredata.assignment.utility.PasswordEncrypt;
import com.oredata.assignment.utility.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final UserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final User_Role_Service userRoleService;
    private final RoleService roleService;
    private final IUserProfileMapper userProfileMapper;
    private final PasswordEncrypt passwordEncrypt;
    Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    public UserProfileService(UserProfileRepository repository, JwtTokenManager jwtTokenManager, User_Role_Service userRoleService, RoleService roleService, IUserProfileMapper userProfileMapper, PasswordEncrypt passwordEncrypt) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.userProfileMapper = userProfileMapper;
        this.passwordEncrypt = passwordEncrypt;
    }

    public BaseResponseDto signup(SignUpRequest dto) {
        if (repository.existsByEmail(dto.getEmail())) throw new OredataException(ErrorType.EMAIL_ALREADY_EXISTS);
        Set<Role> roles = new HashSet<>();
        System.out.println("--------");
        for (String roleName : dto.getRoles()) {
            Optional<Role> role = roleService.findOptionalByName(roleName.toUpperCase());
            if (role.isEmpty()) throw new OredataException(ErrorType.ROLE_NOT_FOUND);
            roles.add(role.get());
        }

        String hashpass=passwordEncrypt.passwordHash(dto.getPassword());
        UserProfile userProfile = save(userProfileMapper.toUserProfile(dto,hashpass));
        for (Role role : roles) {
            userRoleService.saveUserRole(userProfile.getId(), role.getId());
        }
        logger.info("User created: {}", userProfile);
        return BaseResponseDto.builder().message("ok").statusCode(200).build();
    }

    // login
    public LoginResponse login(LoginRequest dto) {
        UserProfile userProfile = repository.findOptionalByEmail(dto.getEmail())
                .orElseThrow(() -> new OredataException(ErrorType.MAIL_NOT_FOUND));

        boolean isMacth=passwordEncrypt.checkPassword(dto.getPassword(), userProfile.getPassword());

        if (!isMacth) throw new OredataException(ErrorType.PASSWORD_NOT_MATCH);
        String token = jwtTokenManager.createToken(userProfile.getId()).get();
        logger.info("login successful. {}", userProfile);
        return LoginResponse.builder().message("ok").statusCode(200).bearerToken(token).build();
    }

    public UserProfileResponse getUserProfileById(Long id) {
        UserProfile userProfile = findById(id).orElseThrow(() -> new OredataException(ErrorType.USER_NOT_FOUND));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public BaseResponseDto updateUserProfile(Long id, UserUpdateRequest dto) {

        UserProfile userProfile = findById(id).orElseThrow(() -> new OredataException(ErrorType.USER_NOT_FOUND));
        userProfile= userProfileMapper.toUserProfile(dto,userProfile);
        update(userProfile);
        logger.info("User updated: {}",userProfile);
        return BaseResponseDto.builder().message("ok").statusCode(200).build();
    }

    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }
}
