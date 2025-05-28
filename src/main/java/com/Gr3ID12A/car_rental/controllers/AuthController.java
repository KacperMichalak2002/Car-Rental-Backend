package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest){
         authenticationService.registerUser(userRequest);
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest){

        String verified = authenticationService.verify(userRequest);

        return new ResponseEntity<>(verified, HttpStatus.OK);
    }

}
