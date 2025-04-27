package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private Long studentId;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private boolean isActive;
    private UserResponseDto userResponse;
}
