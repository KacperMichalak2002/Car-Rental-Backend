package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.AuthProvider;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenType;
import com.Gr3ID12A.car_rental.mappers.UserMapper;
import com.Gr3ID12A.car_rental.repositories.RoleRepository;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public void registerUser(UserRequest userRequest) {

        if(userRepository.findByEmail(userRequest.getEmail()).orElse(null) != null){
            throw new RuntimeException("Email taken");
        }

        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        UserEntity userToRegister = userMapper.toEntity(userRequest);
        userToRegister.setProvider(AuthProvider.LOCAL);
        userToRegister.setEnabled(true);

        RoleEntity role = roleRepository.findByRoleName(RoleName.ROLE_USER).orElse(null);
        userToRegister.setRoles(Set.of(role));

        userRepository.save(userToRegister);

    }

    @Override
    public String verify(UserRequest userRequest) {
        try{
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                            (userRequest.getEmail(), userRequest.getPassword()));

            if(authentication.isAuthenticated()){
                UserEntity user = userRepository.findByEmail(userRequest.getEmail()).orElse(null);

                revokeAllUserTokens(user);

                if(user == null){
                    return "Failed user not found";
                }

                List<String> roles = user.getRoles().stream()
                        .map(role -> role.getRoleName().name())
                        .toList();

                String generatedToken = jwtService.generateToken(userRequest.getEmail(), roles);

                TokenEntity tokenToBeSaved = TokenEntity.builder()
                        .user(user)
                        .token(generatedToken)
                        .tokenType(TokenType.BEARER)
                        .expired(false)
                        .revoked(false)
                        .build();

                tokenRepository.save(tokenToBeSaved);

                return generatedToken;
            }
            return "Failed";
        }catch (Exception e){
            return "Invalid username or password";
        }

    }

    @Override
    public void revokeAllUserTokens(UserEntity user) {
        List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if(validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);

    }

}
