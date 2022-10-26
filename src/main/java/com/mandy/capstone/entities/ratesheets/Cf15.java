package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cf15")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cf15 {
    @Id
    private double rate;
    private double day15;
    private double day30;
    private double day45;
}
