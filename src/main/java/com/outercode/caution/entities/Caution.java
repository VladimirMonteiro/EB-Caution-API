package com.outercode.caution.entities;

import com.outercode.caution.entities.enums.CautionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_id", nullable = false)
    private Military military;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "id.caution", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CautionItem> items = new ArrayList<>();

    // createdBy User
}
