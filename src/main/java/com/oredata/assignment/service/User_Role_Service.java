package com.oredata.assignment.service;

import com.oredata.assignment.repository.User_Role_Repository;
import com.oredata.assignment.repository.entity.Role;
import com.oredata.assignment.repository.entity.User_Role;
import com.oredata.assignment.utility.ServiceManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class User_Role_Service extends ServiceManager<User_Role,Long> {
    private final User_Role_Repository repository;
    Logger logger = LoggerFactory.getLogger(User_Role_Service.class);

    public User_Role_Service(User_Role_Repository repository) {
        super(repository);
        this.repository = repository;
    }
    public void saveUserRole(long userId,long  roleId){
        User_Role userRole=User_Role.builder()
                .roleId(roleId)
                .userId(userId)
                .build();
        logger.info("Save UserRole:{}",userRole);
        save(userRole);
    }
    public List<User_Role> findRolesIdByUserId(long userId){
        return repository.findOptionalByUserId(userId);
    }

    public List<User_Role> findUserIdByRoleId(long roleId){
        return repository.findOptionalByRoleId(roleId);
    }
}
