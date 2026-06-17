package com.outercode.caution.repositories;

import com.outercode.caution.entities.Load;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {
    Optional<Load> findByIdAndUsers_Id(UUID loadId, UUID userId);

    List<Load> findByUsers_Id(UUID userId, Pageable pageable);
    @Query("""
SELECT DISTINCT l FROM Load l
JOIN l.users u
LEFT JOIN FETCH l.items i
LEFT JOIN FETCH i.id.material
WHERE l.id = :loadId AND u.id = :userId
""")
    Optional<Load> findByIdWithItems(UUID loadId, UUID userId);
}
