package com.toll.repository;

import com.toll.model.TollTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TollTransactionRepository extends JpaRepository<TollTransaction, Long> {
    List<TollTransaction> findTop100ByOrderByTransactionTimeDesc();
    long countByStatus(String status);
    List<TollTransaction> findByTransactionTimeBetweenOrderByTransactionTimeDesc(Instant from, Instant to);
}
