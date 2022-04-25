package com.alkemy.ong.vehiclesFactory.web;

import com.alkemy.ong.vehiclesFactory.domain.VehiclesService;
import com.alkemy.ong.vehiclesFactory.data.entities.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class VehiclesController {

    private final VehiclesService vehiclesService;

    public VehiclesController(VehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> save(@RequestBody Vehicle vehicle){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiclesService.save(vehicle));
    }
}
