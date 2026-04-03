package com.outercode.caution.repositories;

import com.outercode.caution.entities.CautionItem;
import com.outercode.caution.entities.pk.CautionItemPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CautionItemRepository extends JpaRepository<CautionItem, CautionItemPk> {
}