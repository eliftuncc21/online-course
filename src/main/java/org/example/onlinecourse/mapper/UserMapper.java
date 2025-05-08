package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.UserRequestDto;
import org.example.onlinecourse.dto.response.UserResponseDto;
import org.example.onlinecourse.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequestDto userRequestDto);

    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "userId", ignore = true)
    void updateUser(UserRequestDto userRequestDto, @MappingTarget User user);
}