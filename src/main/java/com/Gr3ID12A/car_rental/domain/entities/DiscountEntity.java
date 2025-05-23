package com.Gr3ID12A.car_rental.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "discount")
public class DiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;

    private int percent_discount;

    private int const_value_discount;

    private Date date_of_beginning;

    private Date date_of_end;

    private String status;

    private String description;

    @ManyToMany(mappedBy = "discounts", fetch = FetchType.LAZY)
    private Set<CustomerEntity> customers = new HashSet<>();

}
