package com.outercode.caution.services;

import com.outercode.caution.dto.materialDTO.CreateMaterialRequestDTO;
import com.outercode.caution.dto.materialDTO.MaterialResponse;

import java.util.List;

public interface IMaterialService {
    MaterialResponse create(CreateMaterialRequestDTO dto);
    List<MaterialResponse> findAll(int page, int size);
}
