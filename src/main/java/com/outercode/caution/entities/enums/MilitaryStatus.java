package com.outercode.caution.entities.enums;

import lombok.Getter;

@Getter
public enum MilitaryStatus {
    ACTIVE("Ativo"),
    RESERVE("Reserva");

    private final String description;

    MilitaryStatus(String description) {
        this.description = description;
    }
}
