package com.mandy.capstone.repositories.ratesheetsrepo;

import com.mandy.capstone.entities.ratesheets.FicoAdj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FicoAdjRepositories extends JpaRepository<FicoAdj, Integer> {
    List<FicoAdj> findAllByOrderByFicoDesc();
}
