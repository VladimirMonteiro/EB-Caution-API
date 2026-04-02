package com.outercode.caution.repositories;

import com.outercode.caution.entities.LoadItem;
import com.outercode.caution.entities.pk.LoadItemPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadItemRepository extends JpaRepository<LoadItem, LoadItemPk> {
}