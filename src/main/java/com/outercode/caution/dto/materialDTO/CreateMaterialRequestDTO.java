package com.outercode.caution.dto.materialDTO;

import jakarta.validation.constraints.NotBlank;

public record CreateMaterialRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name) {
}
