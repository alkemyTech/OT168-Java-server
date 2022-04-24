package com.alkemy.ong.factory.lowLevel;

import com.alkemy.ong.factory.VehicleTypes;
import com.alkemy.ong.factory.highLevel.Vehicle;
import com.alkemy.ong.factory.highLevel.VehicleFactory;

import java.util.EnumMap;
import java.util.Map;

public class VehicleFactoryImp implements VehicleFactory {

    private static Map<VehicleTypes, Vehicle> mapTypes;
    
    static {
        mapTypes = new EnumMap<>(VehicleTypes.class);
        mapTypes.put(VehicleTypes.MOTORBIKE, new Motorbike());
        mapTypes.put(VehicleTypes.CAR, new Car());
    }

    @Override
    public Vehicle createVehicle(VehicleTypes type) {
        return mapTypes.get(type);
    }


}
