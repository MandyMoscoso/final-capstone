package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BaseRate {
    private double rate;
    private double day15;
    private double day30;
    private double day45;
}
