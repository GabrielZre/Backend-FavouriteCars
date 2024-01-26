package com.wszib.favcars;

import com.wszib.favcars.model.Car;
import com.wszib.favcars.service.CarsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarResource {
    private final CarsService carsService;

    public CarResource(CarsService carsService) {
        this.carsService = carsService;
    }

    @PreAuthorize("hasRole('Admin') OR hasRole('User')")
    @GetMapping("all")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carsService.findAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin') OR hasRole('User')")
    @GetMapping("/find/{id}")
    public ResponseEntity<Car> getAllCarById(@PathVariable("id") Long id)  {
        Car car = carsService.findCarById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car newCar = carsService.addCar(car);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/update")
    public ResponseEntity<Car> updateCar(@RequestBody Car car) {
        Car updateCar = carsService.updateCar(car);
        return new ResponseEntity<>(updateCar, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
        carsService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
