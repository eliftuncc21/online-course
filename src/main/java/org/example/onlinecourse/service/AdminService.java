package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.AdminRequestDto;
import org.example.onlinecourse.dto.response.AdminResponseDto;
import org.example.onlinecourse.mapper.AdminMapper;
import org.example.onlinecourse.model.Admin;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.AdminRepository;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminMapper adminMapper;

    public AdminResponseDto addAdmin(AdminRequestDto adminRequest) {
        User user = userRepository.findById(adminRequest.getUserId()).orElse(null);
        Admin admin = adminMapper.toAdmin(adminRequest);
        admin.setUser(user);
        Admin dbAdmin = adminRepository.save(admin);
        return adminMapper.toAdminResponseDto(dbAdmin);
    }

    public List<AdminResponseDto> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return adminMapper.toAdminResponseDtoList(admins);
    }

    public AdminResponseDto findAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        return adminMapper.toAdminResponseDto(admin);
    }

    public void deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        adminRepository.delete(admin);
    }

    public AdminResponseDto updateAdmin(AdminRequestDto adminRequest, Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        adminMapper.updateAdmin(adminRequest, admin);

        Admin dbAdmin = adminRepository.save(admin);
        return adminMapper.toAdminResponseDto(dbAdmin);
    }
}
