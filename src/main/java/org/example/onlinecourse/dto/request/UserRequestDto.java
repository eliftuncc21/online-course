package org.example.onlinecourse.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.ActiveStatus;
import org.example.onlinecourse.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "İsim boş olamaz.")
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank(message = "Soyisim boş olamaz.")
    private String lastName;

    @NotBlank(message = "Email boş olamaz.")
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    private ActiveStatus isActive;

    @NotBlank
    private String telephoneNumber;

    @NotBlank(message = "Rol boş olamaz.")
    private Role role;
}
