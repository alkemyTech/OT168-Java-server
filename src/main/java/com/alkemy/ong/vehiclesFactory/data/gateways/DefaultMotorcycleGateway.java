package com.alkemy.ong.vehiclesFactory.data.gateways;

import com.alkemy.ong.vehiclesFactory.domain.MotorcycleGateway;
import com.alkemy.ong.vehiclesFactory.data.entities.Motorcycle;
import com.alkemy.ong.vehiclesFactory.data.entities.Vehicle;
import com.alkemy.ong.vehiclesFactory.data.repositories.MotorcycleRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultMotorcycleGateway implements MotorcycleGateway {

    private final MotorcycleRepository motorcycleRepository;

    public DefaultMotorcycleGateway(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    public Vehicle save(Motorcycle motorcycle) {
        return motorcycleRepository.save(motorcycle);
    }
}
