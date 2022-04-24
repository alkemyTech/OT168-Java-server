package com.alkemy.ong.factoryMethodEliana.impl;

import com.alkemy.ong.factoryMethodEliana.entities.Car;
import org.springframework.stereotype.Component;

@Component
public abstract class CarFactory implements VehicleFactory{

    private final Car car;

    public CarFactory(Car car){
        this.car = car;
    }

    @Override
    public Car createVehicles(){
        Car car = new Car();
        return car;
    }
}
