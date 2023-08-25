package com.proje.salad_App.repository;

import com.proje.salad_App.entity.concretes.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phone);

    Admin findByUsernameEquals(String username);
}
