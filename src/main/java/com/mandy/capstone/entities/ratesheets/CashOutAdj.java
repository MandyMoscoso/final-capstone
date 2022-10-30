package com.mandy.capstone.entities.ratesheets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//This class extends AdjRate class and AdjRate has all lombok annotation, so no need for @Data... here

@Entity
@Table(name = "cashout_adj")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashOutAdj extends AdjRate{
    @Id
    private Integer fico;
}
