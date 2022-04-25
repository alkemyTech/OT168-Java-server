package com.alkemy.ong.vehiclesFactory.domain;

import com.alkemy.ong.vehiclesFactory.data.entities.Motorcycle;
import com.alkemy.ong.vehiclesFactory.data.entities.Vehicle;

public interface MotorcycleGateway {
    Vehicle save(Motorcycle motorcycle);
}
