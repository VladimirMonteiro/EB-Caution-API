package com.outercode.caution.repositories;

import com.outercode.caution.entities.LoadItem;
import com.outercode.caution.entities.pk.LoadItemPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadItemRepository extends JpaRepository<LoadItem, LoadItemPk> {
    List<LoadItem> findById_Load_Id(UUID loadId);
    Optional<LoadItem> findById_Load_IdAndId_Material_Id(UUID loadId, UUID materialId);
}
