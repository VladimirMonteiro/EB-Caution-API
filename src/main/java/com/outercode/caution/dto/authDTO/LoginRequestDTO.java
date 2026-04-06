package com.outercode.caution.dto.authDTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "E-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String password) {
}