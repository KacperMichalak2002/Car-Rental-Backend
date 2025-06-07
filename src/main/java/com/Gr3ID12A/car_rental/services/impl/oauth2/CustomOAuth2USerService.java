package com.Gr3ID12A.car_rental.services.impl.oauth2;

import com.Gr3ID12A.car_rental.domain.dto.user.CustomOAuth2User;
import com.Gr3ID12A.car_rental.domain.entities.AuthProvider;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.repositories.RoleRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2USerService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String providerId = userRequest.getClientRegistration().getRegistrationId();

        Optional<UserEntity> existingUser = userRepository.findByEmail(email);
        UserEntity user;

        if(existingUser.isPresent()){
            user = existingUser.get();
            user.setName(name);
        }else {
            user = new UserEntity();
            user.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase()));
            user.setProviderId(providerId);
            user.setName(name);
            user.setEmail(email);
            user.setEnabled(true);

            RoleEntity userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("USER role not found"));
            user.setRoles(Set.of(userRole));

           userRepository.save(user);
        }

        return new CustomOAuth2User(oAuth2User,user);


    }

}
