package com.alkemy.ong.vehiclesFactory.domain;

import com.alkemy.ong.vehiclesFactory.data.entities.Car;
import com.alkemy.ong.vehiclesFactory.data.entities.Motorcycle;
import com.alkemy.ong.vehiclesFactory.data.entities.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class VehiclesService {
    private final CarGateway carGateway;
    private final MotorcycleGateway motorcycleGateway;

    public VehiclesService(CarGateway carGateway, MotorcycleGateway motorcycleGateway) {
        this.carGateway = carGateway;
        this.motorcycleGateway = motorcycleGateway;
    }

    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getType().equals("CAR")) {
            Vehicle car = vehicle;
            carGateway.save((Car) vehicle);
        } else if (vehicle.getType().equals("MOTORCYCLE")) {
            motorcycleGateway.save((Motorcycle) vehicle);
        }
        return vehicle;
    }
}