package com.Gr3ID12A.car_rental.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private ModelEntity model;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification_id", unique = true)
    private SpecificationEntity specification;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(precision = 10, scale = 2)
    private BigDecimal deposit;

    private String availability;

    private String image_url;

    private String description;
}
