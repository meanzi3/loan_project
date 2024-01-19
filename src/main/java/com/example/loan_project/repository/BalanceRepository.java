package com.example.loan_project.repository;

import com.example.loan_project.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
  Optional<Balance> findByApplicationId(Long applicationId);
}
