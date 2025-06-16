package com.Gr3ID12A.car_rental.config;

import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.RefreshTokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.repositories.RefreshTokenRepository;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final JWTService jwtService;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return;

        token = authHeader.substring(7);

        TokenEntity storedToken = tokenRepository.findByToken(token).orElse(null);

        if(storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);

            String email = jwtService.extractUsername(storedToken.getToken());
            UserEntity user = userRepository.findByEmail(email).orElse(null);

            if(user !=null){
                try{
                    RefreshTokenEntity refreshTokenToDelete = refreshTokenRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Refresh token not found"));
                    refreshTokenRepository.delete(refreshTokenToDelete);
                }catch (Exception e){
                    log.error("Error revoking tokens {}", e.getMessage());
                }

            }
        }


    }
}
