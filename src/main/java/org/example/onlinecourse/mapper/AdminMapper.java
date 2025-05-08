package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.AdminRequestDto;
import org.example.onlinecourse.dto.response.AdminResponseDto;
import org.example.onlinecourse.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Context;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(adminRequestDto.getPassword()))")
    Admin toAdmin(AdminRequestDto adminRequestDto, @Context BCryptPasswordEncoder passwordEncoder);

    AdminResponseDto toAdminResponseDto(Admin admin);

    @Mapping(target = "userId", ignore = true)
    void updateAdmin(AdminRequestDto adminRequestDto, @MappingTarget Admin admin);
}
