package com.oredata.assignment.repository;

import com.oredata.assignment.repository.entity.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface User_Role_Repository extends JpaRepository<User_Role,Long> {
    List<User_Role> findOptionalByUserId(Long userId);
    List<User_Role> findOptionalByRoleId(Long roleId);
}
