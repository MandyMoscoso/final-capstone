package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//This class extends Baserate class and Baserate has all lombok annotation, so no need for @Data... here
@Entity
@Table(name = "cf15")
public class Cf15 extends BaseRate{
}
