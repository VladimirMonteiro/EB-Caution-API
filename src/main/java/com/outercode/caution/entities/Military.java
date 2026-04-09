package com.outercode.caution.entities;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.entities.enums.MilitaryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
@Builder
public class Military {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String warName;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String cia;

    @Column(nullable = false)
    private String pel;

    @Column(nullable = false)
    private String grad;

    @Enumerated(EnumType.STRING)
    private MilitaryStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "military", fetch = FetchType.LAZY)
    private List<Caution> cautions = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "militaries", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public static Military create (CreateMilitaryRequestDTO dto) {
        return Military.builder()
                .warName(dto.warName())
                .cpf(dto.cpf())
                .email(dto.email())
                .phone(dto.phone())
                .cia(dto.cia())
                .pel(dto.pel())
                .grad(dto.grad())
                .status(dto.status())
                .build();
    }
}