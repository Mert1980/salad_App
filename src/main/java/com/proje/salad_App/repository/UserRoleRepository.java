package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.UserRole;
import com.proje.salad_App.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    @Query("select r from UserRole r where r.roleType = ?1")
    Optional<UserRole> findByERoleEquals(RoleType roleType);

    @Query("select (count(r)>0) from UserRole r where r.roleType= ?1")
    boolean existsByERoleEquals(RoleType roleType);
}
