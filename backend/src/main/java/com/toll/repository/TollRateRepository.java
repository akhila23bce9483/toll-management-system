package com.toll.repository;

import com.toll.model.TollRate;
import com.toll.model.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TollRateRepository extends JpaRepository<TollRate, Long> {
    Optional<TollRate> findByPlazaIdAndVehicleClassAndActiveTrue(Long plazaId, VehicleClass vehicleClass);
}
