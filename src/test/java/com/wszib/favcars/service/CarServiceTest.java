package com.wszib.favcars.service;

import com.wszib.favcars.exception.CarNotFoundException;
import com.wszib.favcars.model.Car;
import com.wszib.favcars.repo.CarsRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarsRepo carsRepo;

    private CarsService carsService;

    @BeforeEach
    void beforeEach() {
        carsService = new CarsService(carsRepo);
    }


    @Test
    void shouldFindExistingCar() {
        Long existingCarId = 6L;
        Car existingCar = new Car();
        existingCar.setId(existingCarId);

        Mockito.when(carsRepo.findCarById(existingCarId)).thenReturn(Optional.of(existingCar));

        Car resultCar = carsService.findCarById(existingCarId);
        Assertions.assertEquals(existingCar, resultCar);
    }

    @Test
    void shouldThrowExceptionWhenFindingNonExistingCar() {
        Mockito.when(carsRepo.findCarById(6L)).thenReturn(Optional.empty());

        Assertions.assertThrows(CarNotFoundException.class, () -> carsService.findCarById(6L));
    }

    @Test
    void shouldDeleteExistingCar() {
        Long existingCarId = 17L;
        Mockito.when(carsRepo.existsById(existingCarId)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> carsService.deleteCar(existingCarId));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCar() {
        Long nonExistingCarId = 17L;
        Mockito.when(carsRepo.existsById(nonExistingCarId)).thenReturn(false);

        Assertions.assertThrows(CarNotFoundException.class, () -> carsService.deleteCar(nonExistingCarId));
    }

    @Test
    void shouldUpdateExistingCar() {
        Long existingCarId = 6L;
        Mockito.when(carsRepo.existsById(existingCarId)).thenReturn(true);

        Car existingCar = new Car();
        existingCar.setId(existingCarId);

        Assertions.assertDoesNotThrow(() -> carsService.updateCar(existingCar));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCar() {
        Mockito.when(carsRepo.existsById(6L)).thenReturn(false);
        Car updatedCar = new Car();
        updatedCar.setId(6L);

        Assertions.assertThrows(CarNotFoundException.class, () -> carsService.updateCar(updatedCar));
    }

    @Test
    void shouldAddCarSuccessfully() {
        // Arrange
        Car newCar = new Car();

        // Mocking repository behavior
        Mockito.when(carsRepo.save(newCar)).thenReturn(newCar);

        // Act
        Car addedCar = carsService.addCar(newCar);

        // Assert
        assertNotNull(addedCar);
        assertNotNull(addedCar.getCarCode());
        Mockito.verify(carsRepo, times(1)).save(newCar);
    }
}
