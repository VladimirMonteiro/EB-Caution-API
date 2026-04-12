package com.outercode.caution.repositories;

import com.outercode.caution.entities.Military;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MilitaryRepository extends JpaRepository<Military, UUID> {

    Optional<Military> findByCpf(String cpf);
    List<Military> findByUsers_Id(UUID userId, Pageable pageable);
    Optional<Military> findByIdAndUsers_Id(UUID militaryId, UUID userId);
}