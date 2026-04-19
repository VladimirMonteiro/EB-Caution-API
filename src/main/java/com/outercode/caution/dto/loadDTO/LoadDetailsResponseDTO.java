package com.outercode.caution.dto.loadDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.outercode.caution.dto.LoadItemDTO.LoadItemResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LoadDetailsResponseDTO(
        UUID loadId,
        String pelName,
        String cia,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime createdAt,
        List<LoadItemResponse> items) {
}
