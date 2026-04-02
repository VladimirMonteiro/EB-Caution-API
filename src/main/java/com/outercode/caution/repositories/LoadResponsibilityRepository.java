package com.outercode.caution.repositories;

import com.outercode.caution.entities.LoadResponsibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoadResponsibilityRepository extends JpaRepository<LoadResponsibility, UUID> {
}
