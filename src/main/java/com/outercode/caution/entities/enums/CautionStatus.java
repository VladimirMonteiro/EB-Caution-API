package com.outercode.caution.entities.enums;

import lombok.Getter;

@Getter
public enum CautionStatus {
    ACTIVE("Ativo"),
    DELIVERED("Entregue"),
    PARTIALLY_DELIVERED("Entrega parcial"),
    CANCELED("Cancelada");

    private final String description;

    CautionStatus(String description) {
        this.description = description;
    }
}
