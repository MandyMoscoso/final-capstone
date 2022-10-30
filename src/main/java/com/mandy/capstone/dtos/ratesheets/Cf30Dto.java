package com.mandy.capstone.dtos.ratesheets;

import com.mandy.capstone.entities.ratesheets.BaseRate;
import com.mandy.capstone.entities.ratesheets.Cf30;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cf30Dto {
    @Id
    private double rate;
    private double day15;
    private double day30;
    private double day45;

    public Cf30Dto (Cf30 cf30){
        this.rate = cf30.getRate();
        this.day15 = cf30.getDay15();
        this.day30= cf30.getDay30();
        this.day45= cf30.getDay45();
    };
}
