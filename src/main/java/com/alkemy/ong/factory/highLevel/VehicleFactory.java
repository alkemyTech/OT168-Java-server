package com.alkemy.ong.factory.highLevel;

import com.alkemy.ong.factory.VehicleTypes;
import org.springframework.stereotype.Component;

@Component
public interface VehicleFactory {
    Vehicle createVehicle(VehicleTypes type);
}
