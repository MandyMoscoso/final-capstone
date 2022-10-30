package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//This class extends Baserate class and Baserate has all lombok annotation, so no need for @Data... here
@Entity
@Table(name = "cf30")
public class Cf30 extends BaseRate{
}
