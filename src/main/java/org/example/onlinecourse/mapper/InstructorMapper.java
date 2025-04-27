package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.InstructorRequestDto;
import org.example.onlinecourse.dto.response.InstructorResponseDto;
import org.example.onlinecourse.model.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface InstructorMapper{
    Instructor toInstructor(InstructorRequestDto instructorRequestDto);

    InstructorResponseDto toInstructorResponseDto(Instructor instructor);

    List<InstructorResponseDto> toInstructorResponseDtoList(List<Instructor> instructorList);

    @Mapping(target = "instructorId", ignore = true)
    void update(InstructorRequestDto instructorRequestDto, @MappingTarget Instructor instructor);
}