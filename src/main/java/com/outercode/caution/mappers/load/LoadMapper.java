package com.outercode.caution.mappers.load;

import com.outercode.caution.dto.LoadItemDTO.LoadItemResponse;
import com.outercode.caution.dto.loadDTO.LoadDetailsResponseDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.entities.Load;
import com.outercode.caution.entities.LoadItem;

import java.util.List;

public class LoadMapper {

    public static LoadResponse toLoadResponse(Load load) {
        return new LoadResponse(
                load.getId(),
                load.getPelName(),
                load.getCia(),
                load.getCreatedAt()
        );
    }

    public static LoadDetailsResponseDTO toLoadDetailsResponse(Load load, List<LoadItem> items) {
        var loadItems = items.stream()
                .map(LoadMapper::toLoadItemResponse)
                .toList();

        return new LoadDetailsResponseDTO(
                load.getId(),
                load.getPelName(),
                load.getCia(),
                load.getCreatedAt(),
                loadItems
        );
    }

    private static LoadItemResponse toLoadItemResponse(LoadItem item) {
        return new LoadItemResponse(
                item.getMaterial().getId(),
                item.getMaterial().getName(),
                item.getExpectedQuantity(),
                item.getAvailableQuantity(),
                item.getDescription()
        );
    }
}
