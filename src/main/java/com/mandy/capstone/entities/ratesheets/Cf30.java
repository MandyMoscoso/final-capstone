package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cf30")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cf30 extends BaseRate{
    @Id
    private double rate;
    private double day15;
    private double day30;
    private double day45;
}
