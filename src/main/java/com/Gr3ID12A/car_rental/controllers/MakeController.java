package com.Gr3ID12A.car_rental.controllers;


import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeRequest;
import com.Gr3ID12A.car_rental.services.MakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/makes")
@RequiredArgsConstructor
public class MakeController {

    private final MakeService makeService;

    @GetMapping
    public ResponseEntity<List<MakeDto>> listMakes(){
        List<MakeDto> makes = makeService.listMakes();
        return ResponseEntity.ok(makes);
    }

    @PostMapping
    public ResponseEntity<MakeDto> createMake(
            @Valid @RequestBody MakeRequest makeRequest){
        MakeDto savedMake = makeService.createMake(makeRequest);
        return new ResponseEntity<>(savedMake, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMake(@Valid @PathVariable UUID id){
        makeService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
