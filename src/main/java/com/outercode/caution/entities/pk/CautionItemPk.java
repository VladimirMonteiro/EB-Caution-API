package com.outercode.caution.entities.pk;

import com.outercode.caution.entities.Caution;
import com.outercode.caution.entities.Material;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CautionItemPk {

    @ManyToOne
    @JoinColumn(name = "caution_id", nullable = false, foreignKey =
    @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "caution_fk"))
    private Caution caution;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false, foreignKey =
    @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "material_fk"))
    private Material material;

    public CautionItemPk(UUID cautionId, UUID materialId) {
        caution.setId(cautionId);
        material.setId(materialId);
    }
}