package com.Gr3ID12A.car_rental.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rental")
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private CarEntity car;

    private LocalDate date_of_rental;

    private LocalDate date_of_return;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pick_up_place_id")
    private PickUpPlaceEntity pick_up_place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_place_id")
    private ReturnPlaceEntity return_place;

    @Column(precision = 10, scale = 2)
    private BigDecimal total_cost;

    private String status;
}
