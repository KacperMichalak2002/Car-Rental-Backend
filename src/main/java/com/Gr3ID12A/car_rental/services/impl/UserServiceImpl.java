package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.mappers.UserMapper;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public void registerUser(UserRequest userRequest) {

        if(userRepository.findByUsername(userRequest.getUsername()) != null){
            throw new RuntimeException("Username taken");
        }

        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        UserEntity userToRegister = userMapper.toEntity(userRequest);
        userRepository.save(userToRegister);

    }
}
