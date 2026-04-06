package com.outercode.caution.entities;

import com.outercode.caution.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String warName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_militaries", //
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "military_id")
    )
    private List<Military> militaries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Load> loads = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Caution> cautions = new ArrayList<>();

    public User (String warName, String email, String password, Role role) {
        this.warName = warName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        if (this.role == Role.ARMORER) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ARMORER"),
                    new SimpleGrantedAuthority("ROLE_SUB_ARMORER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_SUB_ARMORER"));
    }

    @Override
    public String getUsername () {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired () {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked () {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled () {
        return UserDetails.super.isEnabled();
    }
}