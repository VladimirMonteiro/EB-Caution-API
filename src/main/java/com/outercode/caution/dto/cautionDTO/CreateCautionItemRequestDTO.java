package com.outercode.caution.dto.cautionDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateCautionItemRequestDTO(
        @NotNull(message = "O id do material e obrigatorio.")
        UUID materialId,

        @NotNull(message = "A quantidade e obrigatoria.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantity,

        @FutureOrPresent(message = "A data de entrega nao pode estar no passado.")
        LocalDateTime deliveryDate
) {
}
