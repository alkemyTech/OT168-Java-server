package com.alkemy.ong.vehiclesFactory.data.gateways;

import com.alkemy.ong.vehiclesFactory.domain.CarGateway;
import com.alkemy.ong.vehiclesFactory.data.entities.Car;
import com.alkemy.ong.vehiclesFactory.data.entities.Vehicle;
import com.alkemy.ong.vehiclesFactory.data.repositories.CarRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultCarGateway implements CarGateway {

    private final CarRepository carRepository;

    public DefaultCarGateway(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Vehicle save(Car car) {
        return carRepository.save(car);
    }
}
