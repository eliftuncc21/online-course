package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.StudentRequestDto;
import org.example.onlinecourse.dto.response.StudentResponseDto;
import org.example.onlinecourse.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface StudentMapper{
    Student toStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto toStudentResponseDto(Student student);

    List<StudentResponseDto> toStudentResponseDtoList(List<Student> studentList);

    @Mapping(target = "studentId", ignore = true)
    void update(StudentRequestDto studentRequestDto, @MappingTarget Student student);
}