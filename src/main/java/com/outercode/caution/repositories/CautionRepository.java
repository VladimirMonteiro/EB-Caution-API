package com.outercode.caution.repositories;

import com.outercode.caution.entities.Caution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CautionRepository extends JpaRepository<Caution, UUID> {
}
