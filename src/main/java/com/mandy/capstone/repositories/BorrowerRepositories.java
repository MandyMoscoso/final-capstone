package com.mandy.capstone.repositories;

import com.mandy.capstone.entities.Authorities;
import com.mandy.capstone.entities.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRepositories extends JpaRepository<Borrower, Long> {
    Optional<Borrower> findByUserId(Long userId);
}
