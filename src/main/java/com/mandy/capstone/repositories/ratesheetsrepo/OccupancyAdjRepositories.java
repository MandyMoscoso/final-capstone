package com.mandy.capstone.repositories.ratesheetsrepo;

import com.mandy.capstone.entities.ratesheets.CashOutAdj;
import com.mandy.capstone.entities.ratesheets.OccupancyAdj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OccupancyAdjRepositories extends JpaRepository<OccupancyAdj, String> {
    List<OccupancyAdj> findAllByOrderByOccupancyAsc();
}
