package com.proje.salad_App.repository;


import com.proje.salad_App.entity.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phone);

    User findByUsernameEquals(String username);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);
}
