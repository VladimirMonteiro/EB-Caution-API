package com.outercode.caution.services;

import com.outercode.caution.dto.materialDTO.CreateMaterialRequestDTO;
import com.outercode.caution.dto.materialDTO.MaterialResponse;

public interface IMaterialService {
    MaterialResponse create(CreateMaterialRequestDTO dto);
}
