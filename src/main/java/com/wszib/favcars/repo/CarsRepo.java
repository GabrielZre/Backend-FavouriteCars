package com.wszib.favcars.repo;

import com.wszib.favcars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin("http://localhost:4200")
public interface CarsRepo extends JpaRepository<Car, Long> {
    void deleteCarById(Long id);

    Optional<Car> findCarById(Long id);
}
