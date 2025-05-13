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
@Table(name = "model")
public class ModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "make_id")
    private MakeEntity make;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bodyType_id")
    private BodyTypeEntity bodyType;

    private String name;
}
