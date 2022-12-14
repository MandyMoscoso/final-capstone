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
public class AdjRate {
    private double ltv60;
    private double ltv70;
    private double ltv75;
    private double ltv80;
    private double ltv85;
    private double ltv90;
    private double ltv95;

    public double get(String column){
        switch(column){
            case "ltv60": return ltv60;
            case "ltv70": return ltv70;
            case "ltv75": return ltv75;
            case "ltv80": return ltv80;
            case "ltv85": return ltv85;
            case "ltv90" : return ltv90;
            case "ltv95": return ltv95;
            default: return 100.00;
        }
    }

}
