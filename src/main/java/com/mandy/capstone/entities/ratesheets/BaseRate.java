package com.mandy.capstone.entities.ratesheets;

import lombok.Data;



@Data
public class BaseRate {
    private double rate;
    private double day15;
    private double day30;
    private double day45;

//    public BaseRate (BaseRate baseRate){
//        this.rate = baseRate.rate;
//        this.day15 = baseRate.getDay15();
//        this.day30= baseRate.getDay30();
//        this.day45= baseRate.getDay45();
//    };

}
