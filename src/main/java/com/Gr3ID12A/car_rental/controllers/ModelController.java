package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;
import com.Gr3ID12A.car_rental.services.BodyTypeService;
import com.Gr3ID12A.car_rental.services.MakeService;
import com.Gr3ID12A.car_rental.services.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;
    private final MakeService makeService;
    private final BodyTypeService bodyTypeService;


    @GetMapping
    public ResponseEntity<List<ModelDto>> listModels(){
        List<ModelDto> models = modelService.listModels();
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ModelDto> createModel(@RequestBody ModelRequest modelRequest){
        boolean makeExist = makeService.isExist(modelRequest.getMakeId());
        boolean bodyTypeExist = bodyTypeService.isExist(modelRequest.getBodyTypeId());

        if(!makeExist || !bodyTypeExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ModelDto modelSaved = modelService.createModel(modelRequest);
        return new ResponseEntity<>(modelSaved, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable("id") UUID id){
        modelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
