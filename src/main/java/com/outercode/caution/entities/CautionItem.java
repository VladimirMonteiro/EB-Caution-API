package com.outercode.caution.entities;

import com.outercode.caution.entities.enums.CautionItemStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CautionItem {

    // ID COMPOSTED

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private CautionItemStatus status;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;
}
