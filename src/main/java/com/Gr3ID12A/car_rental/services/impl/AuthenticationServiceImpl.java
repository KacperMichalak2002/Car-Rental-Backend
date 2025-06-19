package com.Gr3ID12A.car_rental.services.impl;


import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.*;
import com.Gr3ID12A.car_rental.services.UsersService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomerService customerService;
    private final UsersService usersService;

    @Override
    public void registerUser(UserRequest userRequest) {

        if(userRepository.findByEmail(userRequest.getEmail()).orElse(null) != null){
            throw new RuntimeException("Email taken");
        }

        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        String result = usersService.createUser(userRequest);

        if(result.equals("Success")){
            log.info("Registered new user");
        }else{
            log.error("User failed to be registered");
        }

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
                    return new AuthResponse("Failed user not found",null,null,null);
                }

                List<String> roles = user.getRoles().stream()
                        .map(role -> role.getRoleName().name())
                        .toList();

                String generatedToken = jwtService.generateToken(userRequest.getEmail(), roles);

                String generatedRefreshToken = refreshTokenService.generateRefreshToken(userRequest.getEmail(), user);


                String customerId = customerService.getCustomerByUserId(user.getId()).getId().toString();

                return new AuthResponse("Success", customerId, generatedToken, generatedRefreshToken);
            }
            return new AuthResponse("Failed",null,null,null);
        }catch (Exception e){
            return new AuthResponse("Invalid username or password",null,null,null);
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
