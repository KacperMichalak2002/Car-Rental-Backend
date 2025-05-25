package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;

public interface UserService {
    void registerUser(UserRequest userRequest);

    String verify(UserRequest userRequest);
}
