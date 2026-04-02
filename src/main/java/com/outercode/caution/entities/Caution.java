package com.outercode.caution.entities;

import com.outercode.caution.entities.enums.CautionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime cautionDate;

    @Enumerated(EnumType.STRING)
    private CautionStatus status;

    private String observations;

    // createdBy User
}
