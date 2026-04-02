package com.outercode.caution.entities.enums;

import lombok.Getter;

@Getter
public enum CautionItemStatus {

    ACTIVE("Ativo"),
    RETURNED("Retornado"),
    LOST("Perdido"),
    DAMAGED("Danificado");

    private final String description;

    CautionItemStatus(String description) {
        this.description = description;
    }
}
