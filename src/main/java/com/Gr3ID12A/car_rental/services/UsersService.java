package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;

public interface UsersService {

     String createUser(UserRequest userRequest);

     void createEmptyCustomer(UserEntity user);
}
