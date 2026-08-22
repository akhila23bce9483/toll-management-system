package com.toll.repository;

import com.toll.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByRegistrationNumberIgnoreCase(String registrationNumber);
    Optional<Vehicle> findByFastagId(String fastagId);
}
