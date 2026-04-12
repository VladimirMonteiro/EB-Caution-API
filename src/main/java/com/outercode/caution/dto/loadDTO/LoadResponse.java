package com.outercode.caution.dto.loadDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoadResponse(
        UUID loadId,
        String pelName,
        String cia,
        @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
        LocalDateTime createdAt) {
}
