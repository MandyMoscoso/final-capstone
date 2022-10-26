package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cf30hb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cf30hb extends BaseRate{
    @Id
    private double rate;
}
