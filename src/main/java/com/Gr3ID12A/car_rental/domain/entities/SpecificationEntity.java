package com.Gr3ID12A.car_rental.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "specification")

public class SpecificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int yearOfProduction;

    private String registration;

    private String vin;

    private String color;

    private int numberOfSeats;

    private double engineCapacity;

    private int horsepower;

    private String gearbox;

    private String driveType;

    private String fuelType;

    private int mileage;

}
