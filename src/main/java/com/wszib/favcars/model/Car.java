package com.wszib.favcars.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String mark;
    private String model;
    private String fuel;
    private String engine;
    private String horsePower;
    private String imageUrl;
    @Column(nullable = false, updatable = false)
    private String carCode;

    public Car() {}

    public Car(String mark, String model, String fuel, String engine, String horsePower, String imageUrl, String carCode) {
        this.mark = mark;
        this.model = model;
        this.fuel = fuel;
        this.engine = engine;
        this.horsePower = horsePower;
        this.imageUrl = imageUrl;
        this.carCode = carCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(String horsePower) {
        this.horsePower = horsePower;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", engine='" + engine + '\'' +
                ", horsePower='" + horsePower + '\'' +
                '}';
    }
}
