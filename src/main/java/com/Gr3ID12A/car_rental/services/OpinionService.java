package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionDto;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionRequest;

import java.util.List;
import java.util.UUID;

public interface OpinionService {
    List<OpinionDto> listOpinions();

    OpinionDto createOpinion(OpinionRequest opinionRequest);

    boolean isExist(UUID id);

    OpinionDto partialUpdate(UUID id, OpinionRequest opinionRequest);

    void delete(UUID id);

    List<OpinionDto> listOpinionsByCar(UUID carId);
}
