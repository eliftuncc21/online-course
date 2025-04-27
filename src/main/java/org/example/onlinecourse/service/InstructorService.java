package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.InstructorRequestDto;
import org.example.onlinecourse.dto.response.InstructorResponseDto;
import org.example.onlinecourse.mapper.InstructorMapper;
import org.example.onlinecourse.model.Instructor;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.InstructorRepository;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;
    private final InstructorMapper instructorMapper;

    public InstructorResponseDto addInstructors(InstructorRequestDto instructorRequestDto) {
        User user = userRepository.findById(instructorRequestDto.getUserId()).orElse(null);
        Instructor instructor = instructorMapper.toInstructor(instructorRequestDto);
        instructor.setUser(user);
        Instructor dbInstructor = instructorRepository.save(instructor);
        return instructorMapper.toInstructorResponseDto(dbInstructor);
    }

    public List<InstructorResponseDto> listInstructors() {
        List<Instructor> instructorList = instructorRepository.findAll();
        return instructorMapper.toInstructorResponseDtoList(instructorList);
    }

    public InstructorResponseDto findInstructorsById(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        return instructorMapper.toInstructorResponseDto(instructor);
    }

    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }

    public InstructorResponseDto updateInstructor(Long id, InstructorRequestDto instructorRequestDto) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        instructorMapper.update(instructorRequestDto, instructor);

        Instructor instructorDb = instructorRepository.save(instructor);
        return instructorMapper.toInstructorResponseDto(instructorDb);
    }
}
