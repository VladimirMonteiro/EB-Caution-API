package com.outercode.caution.entities.pk;


import com.outercode.caution.entities.Load;
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
public class LoadItemPk {

    @ManyToOne
    @JoinColumn(name = "load_id", nullable = false, foreignKey =
    @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "load_fk"))
    private Load load;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false, foreignKey =
    @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "material_fk"))
    private Material material;

    public LoadItemPk(UUID loadId, UUID materialId) {
        load.setId(loadId);
        material.setId(materialId);
    }
}