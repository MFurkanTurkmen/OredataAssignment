package com.oredata.assignment.repository;

import com.oredata.assignment.repository.entity.Role;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findOptionalByName(String name);
    @Query(nativeQuery = true, value = "SELECT r.name FROM tbl_user u " +
            "INNER JOIN user_role ur ON u.id = ur.user_id " +
            "INNER JOIN tbl_roles r ON ur.role_id = r.id " +
            "WHERE u.id = ?1")
    Set<String> findRolesByUserId(Long userId);
    boolean existsByNameIgnoreCase(String name);
}
