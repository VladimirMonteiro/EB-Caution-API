package com.outercode.caution.dto.militaryDTO;

import com.outercode.caution.entities.enums.MilitaryStatus;

import java.util.UUID;

public record MilitaryResponse(
        UUID id,
        String warName,
        String email,
        String grad,
        String cia,
        String pel,
        String phone,
        MilitaryStatus status) {
}