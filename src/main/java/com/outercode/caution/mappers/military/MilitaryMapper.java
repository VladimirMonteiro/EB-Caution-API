package com.outercode.caution.mappers.military;

import com.outercode.caution.dto.militaryDTO.MilitaryResponse;
import com.outercode.caution.entities.Military;

public class MilitaryMapper {

    public static MilitaryResponse toMilitaryResponse (Military military) {
        return new MilitaryResponse(
                military.getId(),
                military.getWarName(),
                military.getEmail(),
                military.getGrad(),
                military.getCia(),
                military.getPel(),
                military.getPhone(),
                military.getStatus()
        );
    }
}
