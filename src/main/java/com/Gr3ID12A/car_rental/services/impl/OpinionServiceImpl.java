package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionDto;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionRequest;
import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import com.Gr3ID12A.car_rental.domain.entities.OpinionEntity;
import com.Gr3ID12A.car_rental.mappers.OpinionMapper;
import com.Gr3ID12A.car_rental.repositories.OpinionRepository;
import com.Gr3ID12A.car_rental.services.CarService;
import com.Gr3ID12A.car_rental.services.CustomerService;
import com.Gr3ID12A.car_rental.services.OpinionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService {
    private final OpinionRepository opinionRepository;
    private final OpinionMapper opinionMapper;
    private final CustomerService customerService;
    private final CarService carService;

    @Override
    public List<OpinionDto> listOpinions() {
        return opinionRepository.findAll()
                .stream()
                .map(opinionMapper::toDto)
                .toList();
    }

    @Override
    public OpinionDto createOpinion(OpinionRequest opinionRequest) {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(opinionRequest.getCustomerId());

        CarEntity car = new CarEntity();
        car.setId(opinionRequest.getCarId());

        OpinionEntity opinionToSave = opinionMapper.toEntity(opinionRequest);

        opinionToSave.setCustomer(customer);
        opinionToSave.setCar(car);

        OpinionEntity savedOpinion = opinionRepository.save(opinionToSave);

        return opinionMapper.toDto(savedOpinion);
    }

    @Override
    public boolean isExist(UUID id) {
        return opinionRepository.existsById(id);
    }

    @Override
    public OpinionDto partialUpdate(UUID id, OpinionRequest opinionRequest) {
        OpinionEntity opinionToUpdate = opinionMapper.toEntity(opinionRequest);

        OpinionEntity updatedOpinion = opinionRepository.findById(id).map(existingOpinion ->{
            Optional.ofNullable(opinionToUpdate.getRating()).ifPresent(existingOpinion::setRating);
            Optional.ofNullable(opinionToUpdate.getDescription()).ifPresent(existingOpinion::setDescription);
            return opinionRepository.save(existingOpinion);
        }).orElseThrow(() -> new EntityNotFoundException("Opinion not found"));

        return opinionMapper.toDto(updatedOpinion);
    }

    @Override
    public void delete(UUID id) {
        opinionRepository.deleteById(id);
    }

    @Override
    public List<OpinionDto> listOpinionsByCar(UUID carId) {
        return opinionRepository.findAllByCar_Id(carId)
                .stream()
                .map(opinionMapper::toDto)
                .toList();
    }
}
