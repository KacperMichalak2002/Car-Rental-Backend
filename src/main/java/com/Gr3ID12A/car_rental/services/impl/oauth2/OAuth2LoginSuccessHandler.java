package com.Gr3ID12A.car_rental.services.impl.oauth2;

import com.Gr3ID12A.car_rental.domain.dto.user.CustomOAuth2User;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenType;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
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
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

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

        String token = jwtService.generateToken(oAuth2User.getEmail(),roles);

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token",token)
                .build().toUriString();

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

}
