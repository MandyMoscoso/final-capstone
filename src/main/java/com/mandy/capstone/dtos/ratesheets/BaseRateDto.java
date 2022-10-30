package com.mandy.capstone.dtos.ratesheets;

import com.mandy.capstone.entities.ratesheets.BaseRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRateDto {
    private double rate;
    private double day15;
    private double day30;
    private double day45;

    public BaseRateDto (BaseRate baseRate){
        this.rate = baseRate.getRate();
        this.day15 = baseRate.getDay15();
        this.day30= baseRate.getDay30();
        this.day45= baseRate.getDay45();
    };
}
