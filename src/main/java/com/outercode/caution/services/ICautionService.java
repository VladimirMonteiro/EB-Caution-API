package com.outercode.caution.services;

import com.outercode.caution.dto.cautionDTO.CautionResponse;
import com.outercode.caution.dto.cautionDTO.CreateCautionRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ICautionService {
    CautionResponse create(UUID userId, CreateCautionRequestDTO dto);
    List<CautionResponse> findAll(UUID userId, int page, int size);
}
