package com.outercode.caution.services;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.dto.militaryDTO.MilitaryResponse;

import java.util.List;
import java.util.UUID;

public interface IMilitaryService {

    MilitaryResponse create(CreateMilitaryRequestDTO dto);
    List<MilitaryResponse> findAll(UUID userId, int page, int size);
    MilitaryResponse findById(UUID userId, UUID militaryId);
}