package com.outercode.caution.services;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.dto.militaryDTO.MilitaryResponse;

public interface IMilitaryService {

    MilitaryResponse create(CreateMilitaryRequestDTO dto);
}