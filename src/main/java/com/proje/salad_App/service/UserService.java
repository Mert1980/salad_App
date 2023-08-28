package com.proje.salad_App.service;

import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.entity.concretes.UserRole;
import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.exeption.DuplicateUserException;
import com.proje.salad_App.exeption.UserNotFoundException;
import com.proje.salad_App.payload.request.UserRequest;
import com.proje.salad_App.payload.response.UserResponse;
import com.proje.salad_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
        checkDuplicate(request.getUsername(), request.getPhoneNumber());

        User newUser = createUserForSave(request);

        // UserRole setlenmeli
        newUser.setUserRole(userRoleService.getUserRole(RoleType.USER));

        // Şifreyi şifrele
        String password = request.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        // Yeni kullanıcıyı kaydet
        User savedUser = userRepository.save(newUser);

        // Yanıtı oluştur ve döndür
        return mapUserEntityToResponse(savedUser);
    }

    private User createUserForSave(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    private void checkDuplicate(String username, String phoneNumber) {
        boolean usernameExists = userRepository.existsByUsername(username);
        boolean phoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);

        if (usernameExists) {
            throw new DuplicateUserException("Username already exists: " + username);
        }

        if (phoneNumberExists) {
            throw new DuplicateUserException("Phone number already exists: " + phoneNumber);
        }
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return mapUserEntityToResponse(user);
    }

    private UserResponse mapUserEntityToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        return response;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserEntityToResponse)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        updateUserEntityFromRequest(user, request);

        User updatedUser = userRepository.save(user);
        return mapUserEntityToResponse(updatedUser);
    }

    private void updateIfNotNullOrEmpty(String newValue, Consumer<String> updateFunction) {
        if (newValue != null && !newValue.isEmpty()) {
            updateFunction.accept(newValue);
        }
    }

    private void updateUserEntityFromRequest(User user, UserRequest request) {
        updateIfNotNullOrEmpty(request.getUsername(), user::setUsername);
        updateIfNotNullOrEmpty(request.getPassword(), user::setPassword);
        updateIfNotNullOrEmpty(request.getFirstName(), user::setFirstName);
        updateIfNotNullOrEmpty(request.getLastName(), user::setLastName);
        updateIfNotNullOrEmpty(request.getEmail(), user::setEmail);
    }
}


