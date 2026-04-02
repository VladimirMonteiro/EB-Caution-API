package com.outercode.caution.repositories;

import com.outercode.caution.entities.Military;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MilitaryRepository extends JpaRepository<Military, UUID> {
}
