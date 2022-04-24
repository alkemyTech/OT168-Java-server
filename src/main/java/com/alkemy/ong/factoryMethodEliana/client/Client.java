package com.alkemy.ong.factoryMethodEliana.client;

import com.alkemy.ong.factoryMethodEliana.entities.Vehicle;
import com.alkemy.ong.factoryMethodEliana.impl.VehicleFactory;
import org.springframework.stereotype.Component;

@Component
public class Client {

    private final VehicleFactory vehicleFactory;

    public Client (VehicleFactory vehicleFactory){
        this.vehicleFactory = vehicleFactory;
    }

    public void createCar(){
        Vehicle vehicle = vehicleFactory.createVehicles();
    }

    public void createMotorbike(){
        Vehicle vehicle = vehicleFactory.createVehicles();
    }
}
