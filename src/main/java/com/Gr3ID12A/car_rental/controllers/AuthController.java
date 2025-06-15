package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        String token = authenticationService.verify(userRequest);

        if(token.equals("Invalid username or password") || token.equals("Failed")){
            return new ResponseEntity<>(new AuthResponse(
                    "Login failed",null
            )
            , HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new AuthResponse(
                "Login successful",
                token
        ),HttpStatus.OK);
    }

    @GetMapping("/oauth-success")
    public ResponseEntity<AuthResponse> oauthSuccess(
            @RequestParam String token,
            @RequestParam(required = false) String error
    ) {
        if (error != null) {
            return new ResponseEntity<>(new AuthResponse(
                    "OAuth2 login failed",null
            ),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new AuthResponse(
                "OAuth2 login success",token
        ),HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginForm() {
        return ResponseEntity.ok("Custom login page placeholder");
    }

}
