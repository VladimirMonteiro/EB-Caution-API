package com.outercode.caution.dto.LoadItemDTO;

import java.util.UUID;

public record LoadItemResponse(
        UUID loadId,
        UUID materialId,
        Integer expectedQuantity,
        String description) {
}
