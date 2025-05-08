package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.AdminRequestDto;
import org.example.onlinecourse.dto.response.AdminResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.AdminMapper;
import org.example.onlinecourse.model.Admin;
import org.example.onlinecourse.model.Role;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.AdminRepository;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;
    private final MailService mailService;

    public AdminResponseDto addAdmin(AdminRequestDto adminRequest) {
        if (adminRequest.getRole() != Role.ADMIN) {
            throw new ErrorMessage(
                    MessageType.INVALID_ROLE_ADMIN,
                    "Invalid Role",
                    HttpStatus.BAD_REQUEST
            );
        }
        Admin admin = adminMapper.toAdmin(adminRequest, passwordEncoder);
        adminRepository.save(admin);
        mailService.sendRegistrationMail(admin.getEmail(),admin.getFirstName());
        return adminMapper.toAdminResponseDto(admin);
    }

    public Page<AdminResponseDto> getAllAdmins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").ascending());
        return adminRepository.findAll(pageable).map(adminMapper::toAdminResponseDto);
    }

    public AdminResponseDto findAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new ErrorMessage(
                MessageType.ADMIN_NOT_FOUND,
                "Admin not found",
                HttpStatus.NOT_FOUND
        ));
        return adminMapper.toAdminResponseDto(admin);
    }

    public void deleteAdmin(Long id) {
        getAdmin(id);
        adminRepository.deleteById(id);
    }

    public AdminResponseDto updateAdmin(AdminRequestDto adminRequest, Long id) {
        Admin admin = getAdmin(id);

        adminMapper.updateAdmin(adminRequest, admin);

        adminRepository.save(admin);
        return adminMapper.toAdminResponseDto(admin);
    }

    private Admin getAdmin(Long id) {
        User currentUser = securityUtil.getCurrentUser();
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.ADMIN_NOT_FOUND,
                        "Admin Not Found",
                        HttpStatus.NOT_FOUND
                ));

        if(!admin.getUserId().equals(currentUser.getUserId())){
            throw new ErrorMessage(
                    MessageType.NO_PERMISSION_ADMIN,
                    "No Permission",
                    HttpStatus.FORBIDDEN
            );
        }
        return admin;
    }
}
