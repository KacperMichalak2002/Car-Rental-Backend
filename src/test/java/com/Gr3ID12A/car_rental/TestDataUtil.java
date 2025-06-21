package com.Gr3ID12A.car_rental;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceRequest;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeRequest;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentStatus;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionRequest;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserDto;
import com.Gr3ID12A.car_rental.domain.entities.*;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentName;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.RefreshTokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenType;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Date;
import java.util.List;


import java.time.LocalDateTime;


import java.util.Set;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class TestDataUtil {

    public static MakeEntity createTestMakeEntity(){
        return MakeEntity.builder()
                .name("Volkswagen")
                .build();
    }

    public static MakeDto createTestMakeDto(){
        return MakeDto.builder()
                .name("Volkswagen")
                .build();
    }

    public static MakeRequest createTestMakeRequest() {
        return new MakeRequest("Volkswagen");
    }


    public static BodyTypeEntity createTestBodyTypeEntity() {
        return BodyTypeEntity.builder()
                .name("Minivan")
                .build();
    }

    public static BodyTypeDto createTestBodyTypeDto(){
        return BodyTypeDto.builder()
                .name("Minivan")
                .build();
    }

    public static BodyTypeRequest createTestBodyTypeRequest() {
        return new BodyTypeRequest("SUV");
    }

    public static PersonalDataEntity createTestPersonalDataEntity2() {
        return PersonalDataEntity.builder()
                .email("other@mail.com")
                .first_name("Anna")
                .last_name("Nowak")
                .id_number("EF-111")
                .pesel("12345678901")
                .phone_number("987654321")
                .address(createTestAddressEntity())
                .build();
    }

    public static CustomerRequest createTestCustomerRequest(UUID personalDataId, UUID userId) {
        return CustomerRequest.builder()
                .personalDataId(personalDataId)
                .userId(userId)
                .loyalty_points(200)
                .build();
    }

    public static ModelDto createTestModelDto(){
        return ModelDto.builder()
                .make(createTestMakeDto())
                .bodyType(createTestBodyTypeDto())
                .name("Touran")
                .build();
    }

    public static ModelRequest createTestModelRequest() {
        ModelRequest request = new ModelRequest();
        request.setName("Touran");
        request.setMakeId(UUID.randomUUID());
        request.setBodyTypeId(UUID.randomUUID());
        return request;
    }

    public static SpecificationEntity createTestSpecificationEntity(){
        return SpecificationEntity.builder()
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

    public static SpecificationRequest createTestSpecificationRequest() {
        return SpecificationRequest.builder()
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


    public static ModelEntity createTestModelEntity() {
        return ModelEntity.builder()
                .name("Touran")
                .make(createTestMakeEntity())
                .bodyType(createTestBodyTypeEntity())
                .build();
    }

    public static ModelEntity createTestModelEntityA() {
        return ModelEntity.builder()
                .name("Passat")
                .make(createTestMakeEntity())
                .bodyType(createTestBodyTypeEntity())
                .build();
    }



    public static CarDto createTestCarDto() {
        return CarDto.builder()
                .availability("Available")
                .cost(BigDecimal.valueOf(250.00))
                .description("Description")
                .image_url("/test/images/img.png")
                .model(createTestModelDto())
                .specification(createTestSpecificationDto())
                .build();
    }


    public static final UUID TEST_MODEL_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID TEST_SPECIFICATION_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static CarRequest createTestCarRequest() {
        return CarRequest.builder()
                .availability("Available")
                .cost(BigDecimal.valueOf(250.00))
                .deposit(BigDecimal.valueOf(100.00))
                .description("Description")
                .image_url("/test/images/img.png")
                .modelId(TEST_MODEL_ID)
                .specificationId(TEST_SPECIFICATION_ID)
                .build();
    }


    public static AddressEntity createTestAddressEntity(){
        return AddressEntity.builder()
                .city("Kielce")
                .country("Polska")
                .postal_code("25-518")
                .street("Wolska")
                .street_number("12A")
                .build();
    }

    public static PersonalDataEntity createTestPersonalDataEntity(AddressEntity address) {
        return PersonalDataEntity.builder()
                .first_name("Jan")
                .last_name("Kowalski")
                .phone_number("123456789")
                .address(address)
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
    public static CarEntity createCarEntity() {
        return CarEntity.builder()
                .description("Test Car")
                .availability("Available")
                .cost(new BigDecimal("100.00"))
                .deposit(new BigDecimal("50.00"))
                .build();
    }

    public static AddressRequest createUpdatedAddressRequest() {
        return AddressRequest.builder()
                .city("Warszawa")
                .country("Polska")
                .postal_code("00-001")
                .street("Marszałkowska")
                .street_number("1")
                .build();
    }


    public static PickUpPlaceEntity createTestPickUpPlaceEntity(){
        return PickUpPlaceEntity.builder()
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return UserEntity.builder()
                .email("test@mail.com")
                .enabled(true)
                .name("Jan Kowalki")
                .password(encoder.encode("password"))
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

    public static CustomerRequest createTestCustomerRequest() {
        return CustomerRequest.builder()
                .personalDataId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .loyalty_points(100)
                .build();
    }

    public static RentalEntity createRentalEntity(){
        return RentalEntity.builder()
                .date_of_rental(LocalDate.now())
                .date_of_return(LocalDate.now())
                .status("Completed")
                .car(createTestCarEntity())
                .customer(createTestCustomerEntity())
                .pick_up_place(createTestPickUpPlaceEntity())
                .return_place(createTestReturnPlaceEntity())
                .build();
    }

    public static CarEntity createTestCarEntity() {
        ModelEntity model = createTestModelEntity();
        SpecificationEntity specification = createTestSpecificationEntity();
        return createTestCarEntity(model, specification);
    }

    public static CarEntity createTestCarEntity(ModelEntity model, SpecificationEntity specification) {
        return CarEntity.builder()
                .availability("Available")
                .cost(new BigDecimal("250.00"))
                .deposit(new BigDecimal("100.00"))
                .description("Description")
                .image_url("/test/images/img.png")
                .model(model)
                .specification(specification)
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



    public static PaymentTypeEntity createTestOnlinePaymentType(){
        return PaymentTypeEntity.builder()
                .name(PaymentName.ONLINE)
                .build();
    }

    public static PaymentTypeDto createTestOnlinePaymentTypeDto(){
        return PaymentTypeDto.builder()
                .name(PaymentName.ONLINE.name())
                .build();
    }

    public static PaymentTypeEntity createTestOfflinePaymentType(){
        return PaymentTypeEntity.builder()
                .name(PaymentName.OFFLINE)
                .build();
    }

    public static PaymentTypeDto createTestOfflinePaymentTypeDto(){
        return PaymentTypeDto.builder()
                .name(PaymentName.OFFLINE.name())
                .build();
    }


    public static PaymentEntity createTestOnlinePaymentEntity(){
        return PaymentEntity.builder()
                .title("Payment online")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("cs_test_session")
                .date_of_payment(LocalDate.now())
                .payment_type(createTestOnlinePaymentType())
                .rental(createRentalEntity())
                .build();
    }

    public static PaymentDto createTestOnlinePaymentDto(){
        return PaymentDto.builder()
                .title("Payment online")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("cs_test_session")
                .date_of_payment(LocalDate.now())
                .payment_type(createTestOnlinePaymentTypeDto())
                .rental(createTestRentalDto())
                .build();
    }

    public static PaymentEntity createTestOfflinePaymentEntity(){
        return PaymentEntity.builder()
                .title("Payment offline")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("")
                .date_of_payment(LocalDate.now())
                .payment_type(createTestOfflinePaymentType())
                .rental(createRentalEntity())
                .build();
    }

    public static PaymentDto createTestOfflinePaymentDto(){
        return PaymentDto.builder()
                .title("Payment online")
                .title("Payment offline")
                .cost(BigDecimal.valueOf(1000.00))
                .status(PaymentStatus.COMPLETED.name())
                .sessionId("")
                .date_of_payment(LocalDate.now())
                .payment_type(createTestOfflinePaymentTypeDto())
                .rental(createTestRentalDto())
                .build();
    }

    public static PaymentRequest createTestPaymentRequestOnline(){
        return PaymentRequest.builder()
                .title("Test payment")
                .rentalId(createRentalEntity().getId())
                .cost(BigDecimal.valueOf(1500.00))
                .paymentType(PaymentName.ONLINE)
                .build();
    }

    public static PaymentRequest createTestPaymentRequestOffline(){
        return PaymentRequest.builder()
                .title("Test payment")
                .rentalId(createRentalEntity().getId())
                .cost(BigDecimal.valueOf(1500.00))
                .paymentType(PaymentName.OFFLINE)
                .build();
    }

    public static UserRequest createTestUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("securePassword123");
        userRequest.setRole(RoleName.ROLE_USER.name());
        return userRequest;
    }


    public static UserEntity createTestUserEntity() {
        RoleEntity defaultRole = new RoleEntity();
        defaultRole.setRoleName(RoleName.ROLE_USER);

        return createTestUserEntity(defaultRole);
    }

    public static UserRequest createTestUserRequestWithRole(String role) {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("securePassword123");
        userRequest.setRole(role);
        return userRequest;
    }


    public static UserEntity createTestUserEntity(RoleEntity role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword(encoder.encode("testPassword"));
        user.setEnabled(true);
        user.setProvider(AuthProvider.LOCAL);
        user.setRoles(Set.of(role));
        return user;
    }






    public static List<TokenEntity> createTestTokenEntityList(UserEntity user) {
        TokenEntity token1 = new TokenEntity();
        token1.setId(UUID.randomUUID());
        token1.setUser(user);
        token1.setToken("token1");
        token1.setTokenType(TokenType.BEARER);
        token1.setExpired(false);
        token1.setRevoked(false);

        TokenEntity token2 = new TokenEntity();
        token2.setId(UUID.randomUUID());
        token2.setUser(user);
        token2.setToken("token2");
        token2.setTokenType(TokenType.BEARER);
        token2.setExpired(false);
        token2.setRevoked(false);

        return List.of(token1, token2);
    }


    public static CustomerEntity createTestCustomerEntity(UUID userId) {
        CustomerEntity customer = new CustomerEntity();
        UserEntity user = new UserEntity();
        user.setId(userId);
        customer.setUser(user);
        return customer;
    }

    public static CustomerDto createTestCustomerDto(UUID id) {
        CustomerDto dto = new CustomerDto();
        dto.setId(id);
        dto.setLoyalty_points(100);
        return dto;
    }

    public static RoleEntity createTestUserRole() {
        RoleEntity role = new RoleEntity();
        role.setId(UUID.randomUUID());
        role.setRoleName(RoleName.ROLE_USER);
        return role;
    }

    public static UserEntity createTestLocalUserEntityWithRole(RoleEntity role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        return UserEntity.builder()
                .email("user@example.com")
                .password(encoder.encode("testPassword"))
                .provider(AuthProvider.LOCAL)
                .roles(Set.of(role))
                .enabled(true)
                .build();
    }




    public static UserEntity createTestUserEntityWithEmail(String email) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return UserEntity.builder()
                .email(email)
                .enabled(true)
                .name("Test User")
                .password(encoder.encode("testpassword"))
                .provider(AuthProvider.LOCAL)
                .build();
    }


    public static OpinionRequest createTestOpinionRequest() {
        return OpinionRequest.builder()
                .rating(5)
                .description("Great car!")
                .customerId(UUID.randomUUID())
                .carId(UUID.randomUUID())
                .build();
    }

    public static PersonalDataRequest createTestPersonalDataRequest() {
        PersonalDataRequest request = new PersonalDataRequest();
        request.setFirst_name("Jan");
        request.setLast_name("Kowalski");
        request.setPesel("01234567891");
        request.setId_number("CD-482");
        request.setPhone_number("123456789");
        request.setEmail("test@mail.com");
        request.setAddressId(UUID.randomUUID());
        return request;
    }

    public static UserEntity createTestUserWithRole() {
        RoleEntity role = new RoleEntity();
        role.setRoleName(RoleName.ROLE_USER);

        UserRequest request = createTestUserRequest();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return UserEntity.builder()
                .email(request.getEmail())
                .enabled(true)
                .name("Test User")
                .password(encoder.encode(request.getPassword()))
                .provider(AuthProvider.LOCAL)
                .roles(Set.of(role))
                .build();
    }


    public static RefreshTokenEntity createTestRefreshTokenEntity(String token, UserEntity user, LocalDateTime expiry) {
        return RefreshTokenEntity.builder()
                .token(token)
                .user(user)
                .expiresAt(expiry)
                .tokenType(TokenType.REFRESH_TOKEN)
                .build();
    }

    public static RentalRequest createTestRentalRequest() {
        return RentalRequest.builder()
                .carId(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .pick_up_placeId(UUID.randomUUID())
                .return_placeId(UUID.randomUUID())
                .date_of_rental(LocalDate.now())
                .date_of_return(LocalDate.now().plusDays(5))
                .status("Completed")
                .build();
    }


    public static RentalEntity createTestRentalEntity(CustomerEntity customer,
                                                      CarEntity car,
                                                      PickUpPlaceEntity pickUp,
                                                      ReturnPlaceEntity returnPlace) {
        return RentalEntity.builder()
                .customer(customer)
                .car(car)
                .pick_up_place(pickUp)
                .return_place(returnPlace)
                .date_of_rental(LocalDate.now())
                .date_of_return(LocalDate.now().plusDays(3))
                .total_cost(new BigDecimal("799.99"))
                .status("CONFIRMED")
                .build();
    }


    public static InsuranceEntity createTestInsuranceEntity(RentalEntity rental) {
        InsuranceEntity insurance = new InsuranceEntity();
        insurance.setRental(rental);
        insurance.setInsurance_type("OC + AC");
        insurance.setCost(new BigDecimal("399.99"));
        insurance.setRange_of_insurance("Full coverage for damages, theft and glass");
        return insurance;
    }

    public static InsuranceRequest createTestInsuranceRequest(UUID rentalId) {
        return InsuranceRequest.builder()
                .rentalId(rentalId)
                .insurance_type("OC + AC")
                .cost(123.45)
                .range_of_insurance("Full coverage for damages, theft and glass")
                .build();
    }

    public static PersonalDataRequest createTestPersonalDataRequest(UUID addressId) {
        return PersonalDataRequest.builder()
                .addressId(addressId)
                .first_name("Jan")
                .last_name("Kowalski")
                .pesel("90010112345")
                .id_number("ABC123456")
                .phone_number("123456789")
                .email("jan.kowalski@example.com")
                .build();
    }

    public static PersonalDataRequest createUpdatedPersonalDataRequest(UUID addressId) {
        return PersonalDataRequest.builder()
                .addressId(addressId)
                .first_name("Adam")
                .last_name("Nowak")
                .pesel("85051567890")
                .id_number("XYZ987654")
                .phone_number("987654321")
                .email("adam.nowak@example.com")
                .build();
    }


    public static RentalEntity createCompleteRentalEntity() {
        return RentalEntity.builder()
                .car(createTestCarEntity())
                .customer(createTestCustomerEntity())
                .pick_up_place(createTestPickUpPlaceEntity())
                .return_place(createTestReturnPlaceEntity())
                .total_cost(new BigDecimal("499.99"))
                .date_of_rental(LocalDate.now())
                .date_of_return(LocalDate.now().plusDays(7))
                .status("ACTIVE")
                .build();
    }
    public static ModelEntity createTestModelEntity(MakeEntity make, BodyTypeEntity bodyType) {
        return ModelEntity.builder()
                .name("Corolla")
                .make(make)
                .bodyType(bodyType)
                .build();
    }
    public static CarEntity createTestCarEntity(ModelEntity model) {
        return CarEntity.builder()
                .model(model)
                .specification(null)
                .cost(BigDecimal.valueOf(200))
                .deposit(BigDecimal.valueOf(100))
                .availability("AVAILABLE")
                .image_url("url")
                .description("Test car")
                .build();
    }
    public static PaymentTypeEntity createTestPaymentTypeEntity() {
        return new PaymentTypeEntity();
    }



    public static UserRequest createInvalidUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("wrong@example.com");
        userRequest.setPassword("wrongPassword");
        return userRequest;
    }
    public static RefreshTokenRequest createRefreshTokenRequest() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("test-refresh-token");
        return request;
    }


    public static RoleEntity createTestAdminRole() {
        return RoleEntity.builder()
                .roleName(RoleName.ROLE_ADMIN)
                .build();
    }

    public static CustomerEntity createTestCustomer(UserEntity user, PersonalDataEntity personalDataEntity){
        return CustomerEntity.builder()
                .user(user)
                .personalData(personalDataEntity)
                .date_of_joining(LocalDate.now())
                .loyalty_points(0)
                .build();
    }


    public static PersonalDataEntity createTestPersonalDataEntityWithAddressGivven(AddressEntity address) {
        return PersonalDataEntity.builder()
                .email("test@mail.com")
                .first_name("Jan")
                .last_name("Kowalski")
                .id_number("CD-482")
                .pesel("01234567891")
                .phone_number("123456789")
                .address(address)
                .build();
    }

    public static RoleEntity createUserRole() {
        return RoleEntity.builder()
                .roleName(RoleName.ROLE_USER)
                .build();
    }

    public static CustomerEntity createTestCustomerEntity(UserEntity user, PersonalDataEntity personalData) {
        return CustomerEntity.builder()
                .user(user)
                .personalData(personalData)
                .date_of_joining(LocalDate.now())
                .loyalty_points(0)
                .build();
    }

    public static String getAuthToken(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception {
        UserRequest loginRequest = UserRequest.builder()
                .email(email)
                .password(password)
                .build();

        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(loginResponse, AuthResponse.class);
        return authResponse.getToken();
    }

    public static String encodePassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

}