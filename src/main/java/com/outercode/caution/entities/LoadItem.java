package com.outercode.caution.entities;

import com.outercode.caution.entities.pk.LoadItemPk;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadItem {

    @EmbeddedId
    private LoadItemPk id = new LoadItemPk();

    @Column(nullable = false)
    private Integer expectedQuantity;

    @Column(nullable = false)
    private Integer availableQuantity;

    private String description;

    public LoadItem(Load load, Material material, Integer expectedQuantity, String description) {
        id.setLoad(load);
        id.setMaterial(material);
        this.expectedQuantity = expectedQuantity;
        this.availableQuantity = expectedQuantity;
        this.description = description;
    }

    public UUID getLoad() {
        return id.getLoad().getId();
    }

    public void setLoad(Load load) {
        id.setLoad(load);
    }

    public Material getMaterial() {
        return id.getMaterial();
    }

    public void setTool(Material material) {
        id.setMaterial(material);
    }
}
