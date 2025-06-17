package com.Gr3ID12A.car_rental;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentStatus;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.user.UserDto;
import com.Gr3ID12A.car_rental.domain.entities.*;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentName;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class TestDataUtil {

    public static MakeEntity createMakeEntity(){
        return MakeEntity.builder()
                .name("Volkswagen")
                .build();
    }

    public static MakeDto createMakeDto(){
        return MakeDto.builder()
                .name("Volkswagen")
                .build();
    }


    public static BodyTypeEntity createBodyTypeEntity(){
        return BodyTypeEntity.builder()
                .name("Minivan")
                .build();
    }

    public static BodyTypeDto createBodyTypeDto(){
        return BodyTypeDto.builder()
                .name("Minivan")
                .build();
    }

    public static ModelEntity createModelEntityA(){
        return ModelEntity.builder()
                .make(createMakeEntity())
                .bodyType(createBodyTypeEntity())
                .name("Touran")
                .build();
    }

    public static ModelDto createModelDto(){
        return ModelDto.builder()
                .make(createMakeDto())
                .bodyType(createBodyTypeDto())
                .name("Touran")
                .build();
    }

    public static SpecificationEntity createTestSpecificationEntity(){
        return SpecificationEntity.builder()
                .id(UUID.randomUUID())
                .color("Blue")
                .driveType("FWD")
                .engineCapacity(1.6)
                .fuelType("Diesel")
                .gearbox("Manual")
                .horsepower(130)
                .mileage(320312)
                .numberOfSeats(7)
                .registration("EZD-QW42")
                .vin("5TDKK3DCXCS239548")
                .yearOfProduction(2002)
                .build();
    }

    public static SpecificationDto createTestSpecificationDto(){
        return SpecificationDto.builder()
                .color("Blue")
                .driveType("FWD")
                .engineCapacity(1.6)
                .fuelType("Diesel")
                .gearbox("Manual")
                .horsepower(130)
                .mileage(320312)
                .numberOfSeats(7)
                .registration("EZD-QW42")
                .vin("5TDKK3DCXCS239548")
                .yearOfProduction(2002)
                .build();
    }


    public static PickUpPlaceRequest createTestPickUpPlace(UUID addressId) {
        return PickUpPlaceRequest.builder()
                .name("Kielce Park Technologiczny")
                .addressId(addressId)
                .build();
    }

    public static AddressRequest createTestAddressRequest() {
        return AddressRequest.builder()
                .country("Polska")
                .city("Kielce")
                .postal_code("25-663")
                .street("Olszewskiego")
                .street_number("6")
                .build();
    }

    public static CarEntity createTestCarEntity(){
        return CarEntity.builder()
                .id(UUID.randomUUID())
                .availability("Available")
                .cost(BigDecimal.valueOf(250.00))
                .description("Description")
                .image_url("/test/images/img.png")
                .model(createModelEntityA())
                .specification(createTestSpecificationEntity())
                .build();

    }

    public static CarDto createTestCarDto(){
        return  CarDto.builder().
                availability("Available")
                .cost(BigDecimal.valueOf(250.00))
                .description("Description")
                .image_url("/test/images/img.png")
                .model(createModelDto())
                .specification(createTestSpecificationDto())
                .build();
    }

    public static AddressEntity createTestAddressEntity(){
        return AddressEntity.builder()
                .id(UUID.randomUUID())
                .city("Kielce")
                .country("Polska")
                .postal_code("25-518")
                .street("Wolska")
                .street("12A")
                .build();
    }

    public static AddressDto createTestAddressDto(){
        return AddressDto.builder()
                .city("Kielce")
                .country("Polska")
                .postal_code("25-518")
                .street("Wolska")
                .street("12A")
                .build();
    }


    public static PickUpPlaceEntity createTestPickUpPlaceEntity(){
        return PickUpPlaceEntity.builder()
                .id(UUID.randomUUID())
                .address(createTestAddressEntity())
                .build();
    }

    public static PickUpPlaceDto createTestPickUpPlaceDto(){
        return PickUpPlaceDto.builder()
                .address(createTestAddressDto())
                .build();
    }

    public static ReturnPlaceEntity createTestReturnPlaceEntity(){
        return ReturnPlaceEntity.builder()
                .id(UUID.randomUUID())
                .address(createTestAddressEntity())
                .build();
    }

    public static ReturnPlaceDto createTestReturnPlaceDto(){
        return ReturnPlaceDto.builder()
                .address(createTestAddressDto())
                .build();
    }

    public static PersonalDataEntity createTestPersonalDataEntity(){
        return PersonalDataEntity.builder()
                .id(UUID.randomUUID())
                .email("test@mail.com")
                .first_name("Jan")
                .last_name("Kowalski")
                .id_number("CD-482")
                .pesel("01234567891")
                .phone_number("123456789")
                .address(createTestAddressEntity())
                .build();
    }

    public static PersonalDataDto createTestPersonalDataDtoy(){
        return PersonalDataDto.builder()
                .email("test@mail.com")
                .first_name("Jan")
                .last_name("Kowalski")
                .id_number("CD-482")
                .pesel("01234567891")
                .phone_number("123456789")
                .address(createTestAddressDto())
                .build();
    }

    public static UserEntity createTestLocalUserEntity(){
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .email("test@mail.com")
                .enabled(true)
                .name("Jan Kowalki")
                .password("password")
                .provider(AuthProvider.LOCAL)
                .providerId(null)
                .build();
    }

    public static UserDto createTestLocalUserDto(){
        return UserDto.builder()
                .email("test@mail.com")
                .enabled(true)
                .name("Jan Kowalki")
                .provider(AuthProvider.LOCAL)
                .providerId(null)
                .build();
    }

    public static UserEntity createTestGoogleUserEntity(){
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .email("test@mail.com")
                .enabled(true)
                .name("Jan Kowalki")
                .password("password")
                .provider(AuthProvider.GOOGLE)
                .providerId("google")
                .build();
    }

    public static UserDto createTestGoogleUserDto(){
        return UserDto.builder()
                .email("test@mail.com")
                .enabled(true)
                .name("Jan Kowalki")
                .provider(AuthProvider.GOOGLE)
                .providerId("google")
                .build();
    }


    public static CustomerEntity createTestCustomerEntity(){
        return CustomerEntity.builder()
                .id(UUID.randomUUID())
                .date_of_joining(LocalDate.now())
                .loyalty_points(100)
                .personalData(createTestPersonalDataEntity())
                .user(createTestLocalUserEntity())
                .build();
    }

    public static CustomerDto createTestCustomerDto(){
        return CustomerDto.builder()
                .date_of_joining(LocalDate.now())
                .loyalty_points(100)
                .personalData(createTestPersonalDataDtoy())
                .user(createTestLocalUserDto())
                .build();
    }

    public static RentalEntity createRentalEntity(){
        return RentalEntity.builder()
                .id(UUID.randomUUID())
                .date_of_rental(LocalDate.now())
                .date_of_return(LocalDate.now())
                .status("Completed")
                .car(createTestCarEntity())
                .customer(createTestCustomerEntity())
                .pick_up_place(createTestPickUpPlaceEntity())
                .return_place(createTestReturnPlaceEntity())
                .build();
    }

    public static RentalDto createTestRentalDto(){
        return RentalDto.builder()
                .date_of_rental(LocalDate.now())
                .date_of_return(LocalDate.now())
                .status("Completed")
                .car(createTestCarDto())
                .customer(createTestCustomerDto())
                .pick_up_place(createTestPickUpPlaceDto())
                .return_place(createTestReturnPlaceDto())
                .build();
    }



    public static PaymentTypeEntity createOnlinePaymentType(){
        return PaymentTypeEntity.builder()
                .id(UUID.randomUUID())
                .name(PaymentName.ONLINE)
                .build();
    }

    public static PaymentTypeDto createOnlinePaymentTypeDto(){
        return PaymentTypeDto.builder()
                .name(PaymentName.ONLINE.name())
                .build();
    }

    public static PaymentTypeEntity createOfflinePaymentType(){
        return PaymentTypeEntity.builder()
                .id(UUID.randomUUID())
                .name(PaymentName.OFFLINE)
                .build();
    }

    public static PaymentTypeDto createOfflinePaymentTypeDto(){
        return PaymentTypeDto.builder()
                .name(PaymentName.OFFLINE.name())
                .build();
    }


    public static PaymentEntity createOnlinePaymentEntity(){
        return PaymentEntity.builder()
                .id(UUID.randomUUID())
                .title("Payment online")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("cs_test_session")
                .date_of_payment(LocalDate.now())
                .payment_type(createOnlinePaymentType())
                .rental(createRentalEntity())
                .build();
    }

    public static PaymentDto createOnlinePaymentDto(){
        return PaymentDto.builder()
                .title("Payment online")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("cs_test_session")
                .date_of_payment(LocalDate.now())
                .payment_type(createOnlinePaymentTypeDto())
                .rental(createTestRentalDto())
                .build();
    }

    public static PaymentEntity createOfflinePaymentEntity(){
        return PaymentEntity.builder()
                .id(UUID.randomUUID())
                .title("Payment offline")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("")
                .date_of_payment(LocalDate.now())
                .payment_type(createOfflinePaymentType())
                .rental(createRentalEntity())
                .build();
    }

    public static PaymentDto createOfflinePaymentDto(){
        return PaymentDto.builder()
                .title("Payment online")
                .title("Payment offline")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("")
                .date_of_payment(LocalDate.now())
                .payment_type(createOfflinePaymentTypeDto())
                .rental(createTestRentalDto())
                .build();
    }




}
