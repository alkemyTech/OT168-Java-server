package com.alkemy.ong.vehiclesFactory.data.repositories;

import com.alkemy.ong.vehiclesFactory.data.entities.Motorcycle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends CrudRepository<Motorcycle, Long> {

}
