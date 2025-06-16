package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {

    @Query("""
        select t from TokenEntity t inner join t.user u
        where u.id = :userId and (t.expired = false and t.revoked = false)
        """)
    List<TokenEntity> findAllValidTokensByUser(UUID userId);

    Optional<TokenEntity> findByToken(String token);

}
