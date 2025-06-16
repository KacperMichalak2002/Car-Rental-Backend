package com.Gr3ID12A.car_rental.services.impl.oauth2;

import com.Gr3ID12A.car_rental.domain.dto.user.CustomOAuth2User;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    @Value("${JWT_EXPIRATION}")
    private long jwtExpiration;

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private long refreshTokenExpiration;

    @Value("${app.oauth2.authorized-redirect-uris=http://localhost:3000/oauth2/redirect}") // change to frontend URI where to be redirected after successful login
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        if(response.isCommitted()){
            return;
        }

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        UserEntity user = userRepository.findByEmail(oAuth2User.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .toList();

        String authToken = jwtService.generateToken(oAuth2User.getEmail(),roles);

        String refreshToken = refreshTokenService.generateRefreshToken(oAuth2User.getEmail(), user);

        Cookie authCookie = new Cookie("authToken", authToken);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setPath("/");
        authCookie.setMaxAge((int) jwtExpiration / 1000); // Zamiana na sekundy
        response.addCookie(authCookie);

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int) refreshTokenExpiration);
        response.addCookie(refreshCookie);


        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request,response,redirectUri);
    }

}
