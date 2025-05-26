package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);
}
