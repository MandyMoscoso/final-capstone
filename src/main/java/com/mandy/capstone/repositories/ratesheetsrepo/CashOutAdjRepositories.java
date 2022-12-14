package com.mandy.capstone.repositories.ratesheetsrepo;

import com.mandy.capstone.entities.ratesheets.CashOutAdj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashOutAdjRepositories extends JpaRepository<CashOutAdj, Integer> {
    List<CashOutAdj> findAllByOrderByFicoDesc();
}
