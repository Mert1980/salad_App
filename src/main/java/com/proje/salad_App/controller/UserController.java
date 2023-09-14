package com.proje.salad_App.controller;
import com.proje.salad_App.entity.concretes.User;
import com.proje.salad_App.exeption.UnauthorizedAccessException;
import com.proje.salad_App.payload.request.UserRequest;
import com.proje.salad_App.payload.response.UserResponse;
import com.proje.salad_App.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Burada userDetails nesnesini kullanarak gerekli işlemleri yapabilirsiniz.
        // Örneğin, userDetails.getUsername() ile kullanıcı adını alabilirsiniz.

        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        System.out.println(request);
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> responses = userService.getAllUsers();
        return ResponseEntity.ok(responses);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();

        // Kullanıcı kimliğini çıkarmak için ayrı bir servis kullanabilirsiniz
        String username = authenticatedUser.getUsername();

        // Kullanıcı admin rolüne sahipse veya güncellenen kullanıcı kendi profilini güncelliyorsa işlemi izin ver
        if (isAdmin(authenticatedUser) || id.equals(username)) {
            UserResponse response = userService.updateUser(id, request);
            return ResponseEntity.ok(response);
        } else {
            throw new UnauthorizedAccessException("You are not authorized to update this user.");
        }
    }
    private boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // İlgili işlemi gerçekleştirebilir mi diye kontrol etmek için kodunuzu ekleyin
        // Örneğin, yetkilendirmenin kontrolünü burada yapabilirsiniz.
        // Eğer işlemi gerçekleştirebiliyorsa, aşağıdaki kodları kullanabilirsiniz.

        // UserResponse createdUser = userService.createUser(request); // Bu satırı kaldırdım, çünkü request nesnesi tanımlanmamış.
        userService.deleteUser(id);
        String message = "User successfully deleted";
        return ResponseEntity.ok(message);
    }
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        if (!userResponse.getUserId().equals(authenticatedUser.getId())) {
            throw new UnauthorizedAccessException("You are not authorized to access this user.");
        }

        return ResponseEntity.ok(userResponse);
    }

}
