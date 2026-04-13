package com.outercode.caution.services.imp;

import com.outercode.caution.dto.materialDTO.CreateMaterialRequestDTO;
import com.outercode.caution.dto.materialDTO.MaterialResponse;
import com.outercode.caution.entities.Material;
import com.outercode.caution.repositories.MaterialRepository;
import com.outercode.caution.services.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaterialService implements IMaterialService {

    private final MaterialRepository materialRepository;

    @Override
    @Transactional
    public MaterialResponse create (CreateMaterialRequestDTO dto) {
        var material = new Material();
        material.setName(dto.name());

        materialRepository.save(material);
        return new MaterialResponse(material.getId(), material.getName());
    }
}
