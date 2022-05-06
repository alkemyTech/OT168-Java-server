package com.alkemy.ong.factoryMethodEliana.impl;

import com.alkemy.ong.factoryMethodEliana.entities.Motorbike;
import org.springframework.stereotype.Component;

@Component
public abstract class MotorbikeFactory implements VehicleFactory{

    public final Motorbike motorbike;

    public MotorbikeFactory(Motorbike motorbike){
        this.motorbike = motorbike;
    }

    @Override
    public Motorbike createVehicles(){
        Motorbike motorbike = new Motorbike();
        return motorbike;
    }
}
