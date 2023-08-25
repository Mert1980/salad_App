package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.Admin;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.exeption.ConflictException;
import com.proje.salad_App.payload.request.AdminRequest;
import com.proje.salad_App.payload.response.AdminResponse;
import com.proje.salad_App.payload.response.ResponseMessage;
import com.proje.salad_App.repository.AdminRepository;
import com.proje.salad_App.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public Object save(AdminRequest request) {
        checkDuplicate(request.getUsername(),  request.getPhoneNumber());

        Admin admin = createAdminForSave(request);
        admin.setBuild_in(false);

        if (Objects.equals(request.getUsername(), "Admin")) {
            admin.setBuild_in(true);
        }

        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);

        return ResponseMessage.<AdminResponse>builder()
                .message("Admin saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedAdmin))
                .build();
    }

    public void checkDuplicate(String username,  String phone) {
        if (adminRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }
        else if (adminRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
    }

    protected Admin createAdminForSave(AdminRequest request) {
        return Admin.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();
    }

    private AdminResponse createResponse(Admin admin) {
        return AdminResponse.builder()
                .userId(admin.getId())
                .username(admin.getUsername())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .phoneNumber(admin.getPhoneNumber())
                .email(admin.getEmail())
                .build();
    }

    public Page<Admin> getAllAdmin(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    public String deleteAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent() && admin.get().isBuild_in()) {
            throw new ConflictException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if (admin.isPresent()) {
            adminRepository.deleteById(id);
            return "Admin is deleted successfully";
        }
        return Messages.NOT_FOUND_USER_MESSAGE;
    }

    public long countAllAdmin() {
        return adminRepository.count();
    }
}
