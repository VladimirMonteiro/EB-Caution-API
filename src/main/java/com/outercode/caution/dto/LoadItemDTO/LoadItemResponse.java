package com.outercode.caution.dto.LoadItemDTO;

import java.util.UUID;

public record LoadItemResponse(
        UUID materialId,
        String name,
        Integer expectedQuantity,
        Integer availableQuantity,
        String description) {
}
