package com.outercode.caution.dto.cautionDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.outercode.caution.entities.enums.CautionItemStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CautionItemResponse(
        UUID materialId,
        String materialName,
        Integer quantity,
        CautionItemStatus status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime deliveryDate
) {
}
