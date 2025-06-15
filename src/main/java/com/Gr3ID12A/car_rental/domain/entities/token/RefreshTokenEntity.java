package com.Gr3ID12A.car_rental.domain.entities.token;

import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String token;

    private TokenType tokenType;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

}
