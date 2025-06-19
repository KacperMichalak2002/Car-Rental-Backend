package com.Gr3ID12A.car_rental.domain.entities;

import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;

    private boolean enabled;
}
