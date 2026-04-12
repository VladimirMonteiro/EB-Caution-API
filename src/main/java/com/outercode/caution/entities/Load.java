package com.outercode.caution.entities;

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
public class Load {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String pelName;

    @Column(nullable = false)
    private String cia;

    @ManyToMany(mappedBy = "loads")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "load")
    private List<LoadResponsibility> loadResponsibilities = new ArrayList<>();
}