package com.Gr3ID12A.car_rental.config.filter;

import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.impl.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final MyUserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            try{
                username = jwtService.extractUsername(token);

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    boolean isTokenValid = tokenRepository.findByToken(token)
                            .map(t -> !t.isExpired() && !t.isRevoked())
                            .orElse(false);

                    if(jwtService.validateToken(token, userDetails) && isTokenValid){
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }catch (Exception e){
                log.error("Error extracting username {}",e.getMessage());
            }

        }



        filterChain.doFilter(request,response);


    }
}
