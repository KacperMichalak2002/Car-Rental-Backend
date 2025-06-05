package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.dto.user.UserDto;


public interface UserService {
    UserDto registerUser(UserRequest userRequest);

    String verify(UserRequest userRequest);
}
