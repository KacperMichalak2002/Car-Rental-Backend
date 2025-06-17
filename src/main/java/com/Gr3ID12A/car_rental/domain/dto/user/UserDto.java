package com.Gr3ID12A.car_rental.domain.dto.user;

import com.Gr3ID12A.car_rental.domain.entities.AuthProvider;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;

    private String name;

    private String email;

    private AuthProvider provider;

    private String providerId;

    //private Set<String> roles;

    private boolean enabled;

}
