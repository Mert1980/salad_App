package com.proje.salad_App.controller;

import com.proje.salad_App.entity.concretes.Admin;
import com.proje.salad_App.payload.request.AdminRequest;
import com.proje.salad_App.payload.response.AdminResponse;
import com.proje.salad_App.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody @Valid AdminRequest adminRequest) {
        return ResponseEntity.ok(adminService.save(adminRequest));
    }


    //Not: getALL()  ****************************************************************
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<Admin>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {

        Pageable pageable = adminService.createPageable(page, size, sort, type);
        Page<Admin> admins = adminService.getAllAdmin(pageable);

        return ResponseEntity.ok(admins);
    }

    // Not: update() ****************************************************************
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<AdminResponse> update(@PathVariable Long id, @RequestBody @Valid AdminRequest adminRequest) {
        AdminResponse updatedAdmin = adminService.updateAdmin(id, adminRequest);
        return ResponseEntity.ok(updatedAdmin);
    }

    //Not: delete()  ****************************************************************
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteAdmin(id));
    }
}
