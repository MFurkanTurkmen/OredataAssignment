package com.oredata.assignment.utility;

import com.oredata.assignment.dto.request.RoleSaveRequest;
import com.oredata.assignment.dto.request.SignUpRequest;
import com.oredata.assignment.repository.RoleRepository;
import com.oredata.assignment.repository.entity.Role;
import com.oredata.assignment.service.RoleService;
import com.oredata.assignment.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
public class StartAndRun {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserProfileService userProfileService;
    @PostConstruct
    void init(){
        new Thread(()->{
            createRole();
            createUser();
        }).run();
    }
    void createRole(){
        if (!roleService.existsByNameIgnoreCase("ADMIN"))
        roleService.saveRole(RoleSaveRequest.builder().name("ADMIN").build());
    }
    void createUser(){
        if (!userProfileService.existsByEmail("admin")){
        userProfileService.signup(SignUpRequest.builder()
                        .roles(Collections.singleton("ADMIN"))
                        .email("admin")
                        .password("admin")
                        .name("admin")
                .build());
        }
    }
}
