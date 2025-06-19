package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenRequest;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest){
        try{
            authenticationService.registerUser(userRequest);
            return new ResponseEntity<>("User registered", HttpStatus.CREATED);
        }catch (RuntimeException e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest userRequest){

        AuthResponse response = authenticationService.verify(userRequest);

        if(response.getMessage().equals("Invalid username or password") || response.getMessage().equals("Failed")){
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){

        log.info("Called for refresh token");
        RefreshTokenResponse response = authenticationService.handleRefreshToken(refreshTokenRequest.getRefreshToken());

        if(response.getAuthToken() == null){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
