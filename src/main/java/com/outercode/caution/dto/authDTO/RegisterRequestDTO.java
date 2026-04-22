package com.outercode.caution.dto.authDTO;

import com.outercode.caution.entities.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotBlank(message = "O nome de guerra é obrigatório")
        String warName,
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password,
        @NotBlank(message = "A confirmação de senha é obrigatória.")
        String passwordConfirm,
        Role role,
        @NotBlank(message = "A graduação é obrigatória.")
        String grad) {
}
