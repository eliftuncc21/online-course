package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.AuthorityLevel;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDto extends UserResponseDto{
    private AuthorityLevel authorityLevel;
    private LocalDateTime lastLoginDate;
}
