package com.wszib.favcars.resource;

import com.wszib.favcars.model.Car;
import com.wszib.favcars.repo.CarsRepo;
import com.wszib.favcars.util.JwtUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(value = "memory")
@AutoConfigureMockMvc
public class CarResourceTestIT {

    @Autowired
    private CarsRepo carsRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void beforeEach() {
        carsRepo.findAll().forEach(carsRepo::delete);
    }

    @Test
    @SneakyThrows
    void shouldAddCarWhenRequestingAsAdmin() {
        final Car newCar = new Car();
        newCar.setMark("Bmw");
        newCar.setModel("F30 340i");
        newCar.setEngine("3.0");
        newCar.setFuel("Petrol");
        newCar.setHorsePower("322");
        newCar.setImageUrl("https://donauto.lt/img/efe59c4aed3f801e70cf75df734d331c.png");

        final String newCarJson = objectMapper.writeValueAsString(newCar);

        UserDetails adminUserDetails = mock(UserDetails.class);
        when(adminUserDetails.getUsername()).thenReturn("admin123");
        when(adminUserDetails.getPassword()).thenReturn("admin123");

        String jwtToken = jwtUtil.generateToken(adminUserDetails); // Replace adminUserDetails with actual UserDetails

        mockMvc.perform(MockMvcRequestBuilders.post("/car/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCarJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .header("Authorization", "Bearer " + jwtToken))  // Attach the JWT token to the Authorization header
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertEquals(1, carsRepo.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldNotAddCarWhenRequestingAsAdmin() {
        final Car newCar = new Car();
        newCar.setMark("Bmw");
        newCar.setModel("F30 340i");
        newCar.setEngine("3.0");
        newCar.setFuel("Petrol");
        newCar.setHorsePower("322");
        newCar.setImageUrl("https://donauto.lt/img/efe59c4aed3f801e70cf75df734d331c.png");

        final String newCarJson = objectMapper.writeValueAsString(newCar);

        UserDetails defaultUserDetails = mock(UserDetails.class);
        when(defaultUserDetails.getUsername()).thenReturn("user123");
        when(defaultUserDetails.getPassword()).thenReturn("user123");

        String jwtToken = jwtUtil.generateToken(defaultUserDetails); // Replace adminUserDetails with actual UserDetails

        mockMvc.perform(MockMvcRequestBuilders.post("/car/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCarJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .header("Authorization", "Bearer " + jwtToken))  // Attach the JWT token to the Authorization header
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        Assertions.assertEquals(0, carsRepo.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldUpdateCarWhenRequestingAsAdmin() {
        final Car car = new Car();
        car.setCarCode(UUID.randomUUID().toString());
        final Car savedCar = carsRepo.save(car);

        final Car updateCar = new Car();
        updateCar.setId(car.getId());
        updateCar.setMark("Bmw");
        updateCar.setModel("F30 340i");
        updateCar.setEngine("3.0");
        updateCar.setFuel("Petrol");
        updateCar.setHorsePower("322");
        updateCar.setImageUrl("https://donauto.lt/img/efe59c4aed3f801e70cf75df734d331c.png");

        final String updatedCar = objectMapper.writeValueAsString(updateCar);

        UserDetails adminUserDetails = mock(UserDetails.class);
        when(adminUserDetails.getUsername()).thenReturn("admin123");
        when(adminUserDetails.getPassword()).thenReturn("admin123");

        String jwtToken = jwtUtil.generateToken(adminUserDetails); // Replace adminUserDetails with actual UserDetails

        mockMvc.perform(MockMvcRequestBuilders.put("/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCar)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .header("Authorization", "Bearer " + jwtToken))  // Attach the JWT token to the Authorization header
                .andExpect(MockMvcResultMatchers.status().isOk());


        Assertions.assertEquals(1, carsRepo.findAll().size());
    }

    @Test
    @SneakyThrows
    void shouldNotUpdateCarWhenRequestingAsAdmin() {
        final Car car = new Car();
        car.setCarCode(UUID.randomUUID().toString());
        final Car savedCar = carsRepo.save(car);

        final Car updateCar = new Car();
        updateCar.setId(car.getId());
        updateCar.setMark("Bmw");
        updateCar.setModel("F30 340i");
        updateCar.setEngine("3.0");
        updateCar.setFuel("Petrol");
        updateCar.setHorsePower("322");
        updateCar.setImageUrl("https://donauto.lt/img/efe59c4aed3f801e70cf75df734d331c.png");

        final String updatedCar = objectMapper.writeValueAsString(updateCar);

        UserDetails defaultUserDetails = mock(UserDetails.class);
        when(defaultUserDetails.getUsername()).thenReturn("user123");
        when(defaultUserDetails.getPassword()).thenReturn("user123");

        String jwtToken = jwtUtil.generateToken(defaultUserDetails); // Replace adminUserDetails with actual UserDetails

        mockMvc.perform(MockMvcRequestBuilders.put("/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCar)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .header("Authorization", "Bearer " + jwtToken))  // Attach the JWT token to the Authorization header
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
