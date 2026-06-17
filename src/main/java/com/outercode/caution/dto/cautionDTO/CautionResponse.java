package com.outercode.caution.dto.cautionDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.outercode.caution.entities.enums.CautionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CautionResponse(
        UUID cautionId,
        UUID loadId,
        UUID militaryId,
        String militaryWarName,
        UUID userId,
        CautionStatus status,
        String observations,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime cautionDate,
        List<CautionItemResponse> items
) {
}
