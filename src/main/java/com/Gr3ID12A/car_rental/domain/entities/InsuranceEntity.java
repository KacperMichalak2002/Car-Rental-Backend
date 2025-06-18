package com.Gr3ID12A.car_rental.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "insurance")
public class InsuranceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private RentalEntity rental;

    private String insurance_type;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    private String range_of_insurance;
}
