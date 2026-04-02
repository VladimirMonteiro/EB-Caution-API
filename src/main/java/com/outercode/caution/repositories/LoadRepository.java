package com.outercode.caution.repositories;

import com.outercode.caution.entities.Load;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {
}