package com.toll.repository;

import com.toll.model.TollPlaza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TollPlazaRepository extends JpaRepository<TollPlaza, Long> {
    Optional<TollPlaza> findByCodeIgnoreCase(String code);
}
