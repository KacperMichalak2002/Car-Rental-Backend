package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionDto;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionRequest;
import com.Gr3ID12A.car_rental.services.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/opinions")
@RequiredArgsConstructor
public class OpinionController {
    private final OpinionService opinionService;

    @GetMapping
    public ResponseEntity<List<OpinionDto>> listOpinions(){
        List<OpinionDto> opinions = opinionService.listOpinions();
        return new ResponseEntity<>(opinions, HttpStatus.OK);
    }

    @GetMapping(path = "/car/{carId}")
    public ResponseEntity<List<OpinionDto>> listOpinionsByCar(@PathVariable("carId")UUID carId){
        List<OpinionDto> opinions = opinionService.listOpinionsByCar(carId);
        return new ResponseEntity<>(opinions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OpinionDto> createOpinion(@RequestBody OpinionRequest opinionRequest){
        OpinionDto createdOpinion = opinionService.createOpinion(opinionRequest);
        return new ResponseEntity<>(createdOpinion, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<OpinionDto> opinionPartialUpdate(@PathVariable("id")UUID id, @RequestBody OpinionRequest opinionRequest){
        boolean opinionExist = opinionService.isExist(id);
        if(!opinionExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OpinionDto updatedOpinion = opinionService.partialUpdate(id, opinionRequest);
        return new ResponseEntity<>(updatedOpinion, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteOpinion(@PathVariable("id")UUID id){
        opinionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
