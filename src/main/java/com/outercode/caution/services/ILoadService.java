package com.outercode.caution.services;

import com.outercode.caution.dto.LoadItemDTO.CreateLoadItemRequestDTO;
import com.outercode.caution.dto.LoadItemDTO.LoadItemResponse;
import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadDetailsResponseDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;

import java.util.List;
import java.util.UUID;

public interface ILoadService {
    LoadResponse create(UUID userId, CreateLoadRequestDTO dto);
    LoadItemResponse addLoadItem(UUID userId, UUID loadId, UUID materialId, CreateLoadItemRequestDTO dto);
    void removeLoadItem(UUID userId, UUID loadId, UUID materialId);
    List<LoadResponse> findAll(UUID userId, int page, int size);
    LoadDetailsResponseDTO findById(UUID userId, UUID loadId);
}
