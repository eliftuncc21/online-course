package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.StudentRequestDto;
import org.example.onlinecourse.dto.response.StudentResponseDto;
import org.example.onlinecourse.model.Student;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StudentMapper{
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(studentRequestDto.getPassword()))")
    Student toStudent(StudentRequestDto studentRequestDto, @Context BCryptPasswordEncoder passwordEncoder);

    StudentResponseDto toStudentResponseDto(Student student);

    @Mapping(target = "userId", ignore = true)
    void update(StudentRequestDto studentRequestDto, @MappingTarget Student student);
}