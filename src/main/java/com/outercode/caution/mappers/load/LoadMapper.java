package com.outercode.caution.mappers.load;

import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.entities.Load;

public class LoadMapper {

    public static LoadResponse toLoadResponse(Load load) {
        return new LoadResponse(
                load.getId(),
                load.getPelName(),
                load.getCia(),
                load.getCreatedAt()
        );
    }
}
