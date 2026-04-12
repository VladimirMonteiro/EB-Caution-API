package com.outercode.caution.services;

import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;

import java.util.UUID;

public interface ILoadService {
    LoadResponse create(UUID userId, CreateLoadRequestDTO dto);
}
