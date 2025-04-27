package org.example.onlinecourse.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestDto {
    @NotNull
    Long studentId;
    @NotNull
    Long courseId;
}
