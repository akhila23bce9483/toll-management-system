package com.toll.controller;

import com.toll.model.Vehicle;
import com.toll.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleRepository repository;

    public VehicleController(VehicleRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Vehicle> all() { return repository.findAll(); }

    @GetMapping("/{registrationNumber}")
    public Vehicle get(@PathVariable String registrationNumber) {
        return repository.findByRegistrationNumberIgnoreCase(registrationNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@Valid @RequestBody Vehicle vehicle) {
        return repository.save(vehicle);
    }
}
