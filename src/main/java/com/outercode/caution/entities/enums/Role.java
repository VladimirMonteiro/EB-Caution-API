package com.outercode.caution.entities.enums;

import lombok.Getter;

@Getter
public enum Role {
    ARMORER("Armeiro"),
    SUB_ARMORER("Sub-armeiro");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
