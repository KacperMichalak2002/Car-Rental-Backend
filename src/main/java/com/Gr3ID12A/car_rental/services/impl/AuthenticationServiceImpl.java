package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
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
import com.Gr3ID12A.car_rental.services.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Slf4j
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
    private final RefreshTokenService refreshTokenService;

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
    public AuthResponse verify(UserRequest userRequest) {
        try{
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                            (userRequest.getEmail(), userRequest.getPassword()));

            if(authentication.isAuthenticated()){
                UserEntity user = userRepository.findByEmail(userRequest.getEmail()).orElse(null);

                revokeAllUserTokens(user);

                if(user == null){
                    return new AuthResponse("Failed user not found",null,null);
                }

                List<String> roles = user.getRoles().stream()
                        .map(role -> role.getRoleName().name())
                        .toList();

                String generatedToken = jwtService.generateToken(userRequest.getEmail(), roles);

                String generatedRefreshToken = refreshTokenService.generateRefreshToken(userRequest.getEmail(), user);

                return new AuthResponse("Success", generatedToken, generatedRefreshToken);
            }
            return new AuthResponse("Failed",null,null);
        }catch (Exception e){
            return new AuthResponse("Invalid username or password",null,null);
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

    @Override
    public RefreshTokenResponse handleRefreshToken(String refreshToken) {
        try{
            return refreshTokenService.refreshAccessToken(refreshToken);
        }catch (EntityNotFoundException e){
            log.error("Invalid refresh token {}",e.getMessage());
            return  new RefreshTokenResponse("Invalid refresh token",null);
        }catch (RuntimeException e){
            log.error("Refresh token expired {}",e.getMessage());
            return new RefreshTokenResponse("Refresh token expired",null);
        }


    }

}
