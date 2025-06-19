package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.AuthProvider;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.mappers.UserMapper;
import com.Gr3ID12A.car_rental.repositories.RoleRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.CustomerService;
import com.Gr3ID12A.car_rental.services.PersonalDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService implements com.Gr3ID12A.car_rental.services.UsersService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    private final CustomerService customerService;
    private final PersonalDataService personalDataService;
    private final AddressService addressService;

    @Override
    public String createUser(UserRequest userRequest){
        try{
            UserEntity userToRegister = userMapper.toEntity(userRequest);
            userToRegister.setProvider(AuthProvider.LOCAL);
            userToRegister.setEnabled(true);

            RoleEntity role = roleRepository.findByRoleName(RoleName.ROLE_USER).orElse(null);
            userToRegister.setRoles(Set.of(role));

            userRepository.save(userToRegister);
            createEmptyCustomer(userToRegister);

            return "Success";
        }catch (Exception e){
            log.error("Error creating user {}:", e.getMessage());
            return "Failed";
        }
    }

    @Override
    public void createEmptyCustomer(UserEntity user){
        customerService.createCustomer(CustomerRequest.builder()
                .date_of_joining(LocalDate.now())
                .userId(user.getId())
                .loyalty_points(0)
                .personalDataId(
                        personalDataService.createPersonalData(
                                PersonalDataRequest.builder()
                                        .email(user.getEmail())
                                        .addressId(addressService.createAddress(
                                                AddressRequest.builder()
                                                        .city(null)
                                                        .street(null)
                                                        .street_number(null)
                                                        .country(null)
                                                        .postal_code(null)
                                                        .build()
                                        ).getId())
                                        .first_name(null)
                                        .last_name(null)
                                        .pesel(null)
                                        .id_number(null)
                                        .phone_number(null)

                                        .build()
                        ).getId()
                )
                .build());
    }
}
