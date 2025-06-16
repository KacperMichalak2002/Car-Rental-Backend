package com.Gr3ID12A.car_rental.config;

import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByRoleName(roleName).orElseGet(() -> {
                RoleEntity role = new RoleEntity();
                role.setRoleName(roleName);
                return roleRepository.save(role);
            });
        }
    }
}

