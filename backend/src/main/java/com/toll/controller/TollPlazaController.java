package com.toll.controller;

import com.toll.model.TollPlaza;
import com.toll.repository.TollPlazaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/plazas")
@CrossOrigin(origins = "*")
public class TollPlazaController {
    private final TollPlazaRepository repository;

    public TollPlazaController(TollPlazaRepository repository) { this.repository = repository; }

    @GetMapping
    public List<TollPlaza> all() { return repository.findAll(); }

    @GetMapping("/{id}")
    public TollPlaza get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Toll plaza not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TollPlaza create(@RequestBody TollPlaza plaza) { return repository.save(plaza); }
}
