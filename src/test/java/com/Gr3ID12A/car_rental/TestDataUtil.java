package com.Gr3ID12A.car_rental;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.*;

import java.util.UUID;

public final class TestDataUtil {

    public static CarEntity createTestCarA(final ModelEntity modelEntity, final SpecificationEntity specificationEntity){
        return CarEntity.builder()
                .model(modelEntity)
                .specification(specificationEntity)
                .cost(100.0)
                .deposit(1000.0)
                .availability("Available")
                .image_url("test/path/test/image.png")
                .description("Test descirption")
                .build();
    }

    public static CarRequest createCarRequestTestA(final UUID modelId, final UUID specificationId){
        return CarRequest.builder()
                .modelId(modelId)
                .specificationId(specificationId)
                .cost(100.0)
                .deposit(1000.0)
                .availability("Available")
                .image_url("test/path/test/image.png")
                .description("Test descirption")
                .build();
    }

    public static MakeEntity createMakeEntityA(){
        return MakeEntity.builder()
                .name("Volkswagen")
                .build();
    }

    public static BodyTypeEntity createBodyTypeEntityA(){
        return BodyTypeEntity.builder()
                .name("Minivan")
                .build();
    }

    public static ModelEntity createModelEntityA(){
        return ModelEntity.builder()
                .make(createMakeEntityA())
                .bodyType(createBodyTypeEntityA())
                .name("Touran")
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
}
