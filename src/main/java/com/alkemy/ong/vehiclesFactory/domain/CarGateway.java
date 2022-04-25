package com.alkemy.ong.vehiclesFactory.domain;

import com.alkemy.ong.vehiclesFactory.data.entities.Car;
import com.alkemy.ong.vehiclesFactory.data.entities.Vehicle;

public interface CarGateway {
    Vehicle save(Car car);
}
