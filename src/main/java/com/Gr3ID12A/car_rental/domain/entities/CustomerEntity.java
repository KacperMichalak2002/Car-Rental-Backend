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
@Table(name = "customer")

public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id")
    private PersonalDataEntity personalData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_discounts",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id"))
    private Set<DiscountEntity> discounts = new HashSet<>();

    private String login;

    private String password;

    private Date date_of_joining;

    private int loyalty_points;




}
