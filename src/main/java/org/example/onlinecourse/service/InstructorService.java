package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.InstructorFilterRequest;
import org.example.onlinecourse.dto.request.InstructorRequestDto;
import org.example.onlinecourse.dto.response.InstructorResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.InstructorMapper;
import org.example.onlinecourse.model.Instructor;
import org.example.onlinecourse.model.Role;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.InstructorRepository;
import org.example.onlinecourse.specification.InstructorSpecification;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final InstructorMapper instructorMapper;
    private final SecurityUtil securityUtil;
    private final MailService mailService;

    public InstructorResponseDto addInstructors(InstructorRequestDto instructorRequestDto) {
        if(instructorRequestDto.getRole() != Role.INSTRUCTOR){
            throw new ErrorMessage(
                    MessageType.INVALID_ROLE_INSTRUCTOR,
                    "Invalid Role",
                    HttpStatus.BAD_REQUEST
            );
        }
        Instructor instructor = instructorMapper.toInstructor(instructorRequestDto, passwordEncoder);

        instructorRepository.save(instructor);
        mailService.sendRegistrationMail(instructorRequestDto.getEmail(),instructorRequestDto.getFirstName());
        return instructorMapper.toInstructorResponseDto(instructor);
    }

    public Page<InstructorResponseDto> listInstructors(InstructorFilterRequest filterRequest, int page, int size) {
        Specification<Instructor> specification = InstructorSpecification.getInstructorSpecification(filterRequest);
        Pageable pageable = PageRequest.of(page, size);
        return instructorRepository.findAll(specification, pageable).map(instructorMapper::toInstructorResponseDto);
    }

    public InstructorResponseDto findInstructorsById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.INSTRUCTOR_NOT_FOUND,
                        "Instructor not found",
                        HttpStatus.NOT_FOUND)
                );
        return instructorMapper.toInstructorResponseDto(instructor);
    }

    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.INSTRUCTOR_NOT_FOUND,
                        "Instructor not found",
                        HttpStatus.NOT_FOUND)
                );
        getInstructor(instructor);
        instructorRepository.deleteById(id);
    }

    private void getInstructor(Instructor instructor) {
        User currentUser = securityUtil.getCurrentUser();

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isInstructor = currentUser.getUserId().equals(instructor.getUserId());

        if(!(isAdmin || isInstructor)){
            throw new ErrorMessage(
                    MessageType.NO_OPERATION_PERMISSION,
                    "No Permission",
                    HttpStatus.FORBIDDEN
            );
        }
    }

    public InstructorResponseDto updateInstructor(Long id, InstructorRequestDto instructorRequestDto) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.INSTRUCTOR_NOT_FOUND,
                        "Instructor not found",
                        HttpStatus.NOT_FOUND)
                );
        instructorMapper.update(instructorRequestDto, instructor);

        getInstructor(instructor);

        instructorRepository.save(instructor);
        return instructorMapper.toInstructorResponseDto(instructor);
    }
}
