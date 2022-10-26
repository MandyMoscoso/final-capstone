package com.mandy.capstone.repositories.ratesheetsrepo;

import com.mandy.capstone.entities.ratesheets.CashOutAdj;
import com.mandy.capstone.entities.ratesheets.PropertyTypeAdj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTypeAdjRepositories extends JpaRepository<PropertyTypeAdj, String> {
}
