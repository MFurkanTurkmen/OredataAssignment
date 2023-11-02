package com.oredata.assignment.repository;

import com.oredata.assignment.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findOptionalByEmail(String email);
    boolean existsByEmail(String email);



}
