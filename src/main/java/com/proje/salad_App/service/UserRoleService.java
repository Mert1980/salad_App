package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.UserRole;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.exeption.ConflictException;
import com.proje.salad_App.exeption.ResourceNotFoundException;
import com.proje.salad_App.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  UserRoleService {
    private final UserRoleRepository userRoleRepository;
    public UserRole getUserRole(RoleType roleType) {
        Optional<UserRole> userRole= userRoleRepository.findByERoleEquals(roleType);
        return userRole.orElseThrow(()->new ResourceNotFoundException("Role bulunamadi"));
    }

    // Runner tarafi icin gerekli method (asagidaki)
    public List<UserRole> getAllUserRole() {
        return userRoleRepository.findAll();
    }

    public UserRole save(RoleType roleType) {
        if (userRoleRepository.existsByERoleEquals(roleType)){
            throw new ConflictException("This role is already registered");
        }
        UserRole userRole=UserRole.builder().roleType(roleType).build();
        return userRoleRepository.save(userRole);
    }
}
