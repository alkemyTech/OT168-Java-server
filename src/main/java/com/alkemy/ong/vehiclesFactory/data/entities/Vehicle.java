package com.alkemy.ong.vehiclesFactory.data.entities;

import com.alkemy.ong.vehiclesFactory.data.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Vehicle {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Type type;
    protected String brand;
    protected String model;
    protected String color;
}
