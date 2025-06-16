package com.Gr3ID12A.car_rental.domain.dto.user;

import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User;
    private final UserEntity user;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role ->new SimpleGrantedAuthority(role.getRoleName().name()))
                .toList();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public String getEmail(){
        return user.getEmail();
    }
}
