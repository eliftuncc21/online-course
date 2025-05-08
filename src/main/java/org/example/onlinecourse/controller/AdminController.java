package org.example.onlinecourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.AdminRequestDto;
import org.example.onlinecourse.dto.response.AdminResponseDto;
import org.example.onlinecourse.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/save-admin")
    public AdminResponseDto addAdmin(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        return adminService.addAdmin(adminRequestDto);
    }

    @GetMapping("/list-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AdminResponseDto> listAdmin(@RequestParam int page, @RequestParam int size) {
        return adminService.getAllAdmins(page, size);
    }

    @GetMapping("/list-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminResponseDto findAdmin(@PathVariable long id) {
        return adminService.findAdminById(id);
    }

    @DeleteMapping("/delete-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAdmin(@PathVariable long id) {
        adminService.deleteAdmin(id);
    }

    @PutMapping("/update-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminResponseDto updateAdmin(@PathVariable long id, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        return adminService.updateAdmin(adminRequestDto, id);
    }
}
