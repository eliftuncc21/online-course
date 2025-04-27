package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.AdminRequestDto;
import org.example.onlinecourse.dto.response.AdminResponseDto;
import org.example.onlinecourse.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/save-admin")
    public AdminResponseDto addAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        return adminService.addAdmin(adminRequestDto);
    }

    @GetMapping("/list-admin")
    public List<AdminResponseDto> listAdmin() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/list-admin/{id}")
    public AdminResponseDto findAdmin(@PathVariable long id) {
        return adminService.findAdminById(id);
    }

    @DeleteMapping("/delete-admin/{id}")
    public void deleteAdmin(@PathVariable long id) {
        adminService.deleteAdmin(id);
    }

    @PutMapping("/update-admin/{id}")
    public AdminResponseDto updateAdmin(@PathVariable long id, @RequestBody AdminRequestDto adminRequestDto) {
        return adminService.updateAdmin(adminRequestDto, id);
    }
}
