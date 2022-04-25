package com.alkemy.ong.vehiclesFactory.data.repositories;

import com.alkemy.ong.vehiclesFactory.data.entities.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
}
