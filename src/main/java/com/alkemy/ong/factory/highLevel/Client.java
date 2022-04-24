package com.alkemy.ong.factory.highLevel;

import com.alkemy.ong.factory.VehicleTypes;

public class Client {

    private final VehicleFactory vehicleFactory;

    public Client(VehicleFactory vehicleFactory){
        this.vehicleFactory=vehicleFactory;
    }

    public void getCar(){
        Vehicle car = vehicleFactory.createVehicle(VehicleTypes.CAR);
    }

    public void getMotorbike(){
        Vehicle car = vehicleFactory.createVehicle(VehicleTypes.MOTORBIKE);
    }
}
