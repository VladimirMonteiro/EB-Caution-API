package com.outercode.caution.dto.cautionDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateCautionRequestDTO(
        @NotNull(message = "O id da carga e obrigatorio.")
        UUID loadId,

        @NotNull(message = "O id do militar e obrigatorio.")
        UUID militaryId,

        @Size(max = 255, message = "As observacoes devem ter no maximo 255 caracteres.")
        String observations,

        @NotEmpty(message = "A cautela deve possuir ao menos um item.")
        List<@Valid CreateCautionItemRequestDTO> items
) {
}
