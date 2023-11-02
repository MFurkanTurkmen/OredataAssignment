package com.oredata.assignment.service;

import com.oredata.assignment.dto.request.RoleSaveRequest;
import com.oredata.assignment.dto.response.BaseResponseDto;
import com.oredata.assignment.dto.response.RoleResponse;
import com.oredata.assignment.dto.response.RolesByUserIdResponse;
import com.oredata.assignment.exception.ErrorType;
import com.oredata.assignment.exception.OredataException;
import com.oredata.assignment.repository.RoleRepository;
import com.oredata.assignment.repository.entity.Role;
import com.oredata.assignment.repository.entity.User_Role;
import com.oredata.assignment.utility.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService extends ServiceManager<Role,Long> {
    private final RoleRepository repository;
    private final User_Role_Service userRoleService;
    Logger logger= LoggerFactory.getLogger(RoleService.class);
    public RoleService(RoleRepository repository, User_Role_Service userRoleService) {
        super(repository);
        this.repository = repository;
        this.userRoleService = userRoleService;
    }


    public BaseResponseDto saveRole(RoleSaveRequest dto) {
        if (repository.existsByNameIgnoreCase(dto.getName().toUpperCase())) throw new OredataException(ErrorType.ROLE_ALREADY_EXISTS);
        Role role= Role.builder()
                .name(dto.getName().toUpperCase())
                .build();
        save(role);
        logger.info("Role saved. {}",role);
        return BaseResponseDto.builder().message("ok").statusCode(200).build();
    }


    public RolesByUserIdResponse getRolesByUserId(long userId) {
        Set<RoleResponse> roleResponses= repository.findRolesByUserId(userId).stream()
                .map(role -> RoleResponse.builder().name(role).message("ok").statusCode(200).build()).collect(Collectors.toSet());
        return RolesByUserIdResponse.builder().message("ok").statusCode(200).roleResponses(roleResponses).build();
    }

    // repository
    public Optional<Role> findOptionalByName(String name){
        return repository.findOptionalByName(name);
    }

    public BaseResponseDto deleteRole(long id) {
        Role role= findById(id).orElseThrow(()-> new OredataException(ErrorType.ROLE_NOT_FOUND));
        delete(role);
        logger.info("Role deleted. {}",role);
        return BaseResponseDto.builder().message("ok").statusCode(200).build();
    }


    public boolean existsByNameIgnoreCase(String name){
        return repository.existsByNameIgnoreCase(name);
    }

}
