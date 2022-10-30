package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "property_type_adj")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyTypeAdj extends AdjRate{
    @Id
    private String propertyType;
}
