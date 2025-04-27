package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.AdminRequestDto;
import org.example.onlinecourse.dto.response.AdminResponseDto;
import org.example.onlinecourse.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toAdmin(AdminRequestDto adminRequestDto);

    @Mappings({
            @Mapping(source = "user", target = "userResponse")
    })

    AdminResponseDto toAdminResponseDto(Admin admin);

    List<AdminResponseDto> toAdminResponseDtoList(List<Admin> adminList);

    @Mapping(target = "adminId", ignore = true)
    void updateAdmin(AdminRequestDto adminRequestDto, @MappingTarget Admin admin);
}
