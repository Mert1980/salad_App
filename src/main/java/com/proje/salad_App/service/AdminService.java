package com.proje.salad_App.service;
import com.proje.salad_App.entity.concretes.Admin;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.exeption.AdminNotFoundException;
import com.proje.salad_App.exeption.ConflictException;
import com.proje.salad_App.payload.request.AdminRequest;
import com.proje.salad_App.payload.response.AdminResponse;
import com.proje.salad_App.repository.AdminRepository;
import com.proje.salad_App.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public AdminResponse save(AdminRequest request) {
        checkDuplicate(request.getUsername(), request.getPhoneNumber());

        Admin admin = createAdminForSave(request);
        admin.setBuild_in(false);

        if (Objects.equals(request.getUsername(), "Admin")) {
            admin.setBuild_in(true);
        }

        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);

        return mapAdminEntityToResponse(savedAdmin);
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

    private AdminResponse  mapAdminEntityToResponse(Admin admin) {
        AdminResponse response=new AdminResponse();
        response.setUsername(admin.getUsername());
        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setEmail(admin.getEmail());
        return response;
    }

    public List<AdminResponse> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(this::mapAdminEntityToResponse)
                .collect(Collectors.toList());
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

    public AdminResponse updateAdmin(Long id, AdminRequest request) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.isBuild_in()) {
                throw new ConflictException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
            }

            String newUsername = request.getUsername();
            String newPhoneNumber = request.getPhoneNumber();
            if (!admin.getUsername().equals(newUsername) || !admin.getPhoneNumber().equals(newPhoneNumber)) {
                checkDuplicateForUpdate(admin.getUsername(), admin.getPhoneNumber(), newUsername, newPhoneNumber);
            }

            admin.setUsername(newUsername);
            admin.setFirstName(request.getFirstName());
            admin.setLastName(request.getLastName());
            admin.setEmail(request.getEmail());

            String newPassword = request.getPassword();
            if (newPassword != null) {
                admin.setPassword(passwordEncoder.encode(newPassword));
            }

            Admin updatedAdmin = adminRepository.save(admin);
            return mapAdminEntityToResponse(updatedAdmin);
        } else {
            throw new AdminNotFoundException(Messages.NOT_FOUND_USER_MESSAGE);
        }
    }

    private void checkDuplicateForUpdate(String currentUsername, String currentPhoneNumber, String newUsername, String newPhoneNumber) {
        if (!currentUsername.equals(newUsername) && adminRepository.existsByUsername(newUsername)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, newUsername));
        }

        if (!currentPhoneNumber.equals(newPhoneNumber) && adminRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, newPhoneNumber));
        }
    }
    public Pageable createPageable(int page, int size, String sort, String type) {
        Sort.Direction direction = "desc".equalsIgnoreCase(type) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, size, Sort.by(direction, sort));
    }

    public AdminResponse getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(Messages.NOT_FOUND_USER_MESSAGE));
        return mapAdminEntityToResponse(admin);
    }
}
