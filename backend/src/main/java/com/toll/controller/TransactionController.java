package com.toll.controller;

import com.toll.model.TollTransaction;
import com.toll.service.TollTransactionService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TollTransactionService service;

    public TransactionController(TollTransactionService service) { this.service = service; }

    public record TollRequest(@NotBlank String registrationNumber, @NotNull Long plazaId, @NotBlank String laneNumber) {}

    @PostMapping("/process")
    public TollTransaction process(@RequestBody TollRequest request) {
        return service.process(request.registrationNumber(), request.plazaId(), request.laneNumber());
    }

    @GetMapping("/recent")
    public List<TollTransaction> recent() { return service.recent(); }
}
