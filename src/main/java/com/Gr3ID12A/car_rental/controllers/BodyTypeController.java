package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.mappers.BodyTypeMapper;
import com.Gr3ID12A.car_rental.services.BodyTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/bodyTypes")
@RequiredArgsConstructor
public class BodyTypeController {

    private final BodyTypeService bodyTypeService;
    private final BodyTypeMapper bodyTypeMapper;

    @GetMapping
    public ResponseEntity<List<BodyTypeDto>> listBodyTypes(){
        List<BodyTypeDto> bodyTypes = bodyTypeService.listBodyTypes()
                .stream()
                .map(bodyTypeMapper::toDto)
                .toList();
        return ResponseEntity.ok(bodyTypes);
    }

    @PostMapping
    public ResponseEntity<BodyTypeDto> createBodyType(
            @Valid @RequestBody BodyTypeRequest bodyTypeRequest){
        BodyTypeEntity bodyTypeToCreate = bodyTypeMapper.toEntity(bodyTypeRequest);
        BodyTypeEntity bodyTypeSaved = bodyTypeService.createBodyType(bodyTypeToCreate);
        return new ResponseEntity<>(bodyTypeMapper.toDto(bodyTypeSaved), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBodyType(@PathVariable UUID id){
        bodyTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
