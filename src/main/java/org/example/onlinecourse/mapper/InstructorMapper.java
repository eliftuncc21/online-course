package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.InstructorRequestDto;
import org.example.onlinecourse.dto.response.InstructorResponseDto;
import org.example.onlinecourse.model.Instructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface InstructorMapper{
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(instructorRequestDto.getPassword()))")
    Instructor toInstructor(InstructorRequestDto instructorRequestDto, @Context BCryptPasswordEncoder passwordEncoder);

    InstructorResponseDto toInstructorResponseDto(Instructor instructor);

    @Mapping(target = "userId", ignore = true)
    void update(InstructorRequestDto instructorRequestDto, @MappingTarget Instructor instructor);
}