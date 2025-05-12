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

    private int number_of_seats;

    private double engine_capacity;

    private int horsepower;

    private String gearbox;

    private String drive_type;

    private String fuel_type;

    private int mileage;

}
