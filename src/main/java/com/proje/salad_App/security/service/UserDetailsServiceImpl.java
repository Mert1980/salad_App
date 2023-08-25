package com.proje.salad_App.security.service;


import com.proje.salad_App.entity.concretes.Admin;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.entity.concretes.UserRole;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.repository.AdminRepository;
import com.proje.salad_App.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameEquals(username);
        Admin admin = adminRepository.findByUsernameEquals(username);


        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    buildGrantedAuthorities(user.getUserRole())
            );
        } else if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getPassword(),
                    buildGrantedAuthorities(admin.getUserRole())
            );
        } else {
            // TODO -->Security katmani icin genel exception handle class olusturulacak
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
    }
    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final UserRole userRole) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getRoleType().name()));
        return authorities;
    }

}
