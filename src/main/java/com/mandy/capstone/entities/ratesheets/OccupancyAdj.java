package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "occupancy_adj")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OccupancyAdj extends AdjRate{
    @Id
    private String occupancy;
}
