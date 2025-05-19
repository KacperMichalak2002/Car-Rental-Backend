package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionDto;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionRequest;
import com.Gr3ID12A.car_rental.domain.entities.OpinionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OpinionMapper {
    OpinionDto toDto(OpinionEntity opinionEntity);
    OpinionEntity toEntity(OpinionRequest opinionRequest);
}
