package com.outercode.caution.entities;

import com.outercode.caution.entities.enums.CautionItemStatus;
import com.outercode.caution.entities.pk.CautionItemPk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CautionItem {

    @EmbeddedId
    private CautionItemPk id = new CautionItemPk();

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private CautionItemStatus status;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    public CautionItem(Material material, Caution caution, Integer quantity, CautionItemStatus status,
                       LocalDateTime deliveryDate) {
        id.setMaterial(material);
        id.setCaution(caution);
        this.quantity = quantity;
        this.status = status;
        this.deliveryDate = deliveryDate;
    }

    public UUID getCaution() {
        return id.getCaution().getId();
    }

    public void setCaution(Caution caution) {
        id.setCaution(caution);
    }

    public UUID getMaterial() {
        return id.getMaterial().getId();
    }

    public void setMaterial(Material material) {
        id.setMaterial(material);
    }
}
