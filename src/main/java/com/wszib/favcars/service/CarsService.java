package com.wszib.favcars.service;

import com.wszib.favcars.exception.CarNotFoundException;
import com.wszib.favcars.model.Car;
import com.wszib.favcars.repo.CarsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarsService {
    private final CarsRepo carsRepo;

    @Autowired
    public CarsService(CarsRepo carsRepo) {
        this.carsRepo = carsRepo;
    }

    public Car addCar(Car car) {
        car.setCarCode(UUID.randomUUID().toString());
        return carsRepo.save(car);
    }

    public List<Car> findAllCars() {
        return carsRepo.findAll();
    }

    public Car updateCar(Car car) {
        if (car.getId() == null || !carsRepo.existsById(car.getId())) {
            throw new CarNotFoundException("Car does not exist.");
        }
        return carsRepo.save(car);
    }

    public Car findCarById(Long id) {
        return carsRepo.findCarById(id)
                .orElseThrow(() -> new CarNotFoundException("Car by id " + id + " was not found."));
    }

    public void deleteCar(Long id) {
        if (!carsRepo.existsById(id)) {
            throw new CarNotFoundException("Car by id " + id + " was not found.");
        }
        carsRepo.deleteCarById(id);
    }
}
