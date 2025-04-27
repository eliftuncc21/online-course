package org.example.onlinecourse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.AuthorityLevel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDto {
    @NotBlank(message = "Yetki seviyesi boş olamaz")
    private AuthorityLevel authorityLevel;
    private Long userId;
}
