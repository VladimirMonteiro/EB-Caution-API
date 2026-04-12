package com.outercode.caution.dto.loadDTO;

import jakarta.validation.constraints.NotBlank;

public record CreateLoadRequestDTO(
        @NotBlank(message = "Nome do pelotão/seção é obrigatório.")
        String pelName,
        @NotBlank(message = "A companhia é obrigatória.")
        String cia) {
}
