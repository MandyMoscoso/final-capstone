package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//@MappedSuperclass: The JPA standard specification defines the @MappedSuperclass annotation to allow an entity to inherit properties from a base class.
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRate {
    @Id
    private double rate;
    private double day15;
    private double day30;
    private double day45;

    public BaseRate (BaseRate baseRate){
        this.rate = baseRate.rate;
        this.day15 = baseRate.getDay15();
        this.day30= baseRate.getDay30();
        this.day45= baseRate.getDay45();
    };

}
