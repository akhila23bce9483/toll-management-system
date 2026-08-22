package com.toll.service;

import com.toll.model.*;
import com.toll.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TollTransactionService {
    private final VehicleRepository vehicles;
    private final TollPlazaRepository plazas;
    private final TollRateRepository rates;
    private final WalletRepository wallets;
    private final TollTransactionRepository transactions;

    public TollTransactionService(VehicleRepository vehicles, TollPlazaRepository plazas, TollRateRepository rates,
                                  WalletRepository wallets, TollTransactionRepository transactions) {
        this.vehicles = vehicles;
        this.plazas = plazas;
        this.rates = rates;
        this.wallets = wallets;
        this.transactions = transactions;
    }

    @Transactional
    public TollTransaction process(String registrationNumber, Long plazaId, String laneNumber) {
        Vehicle vehicle = vehicles.findByRegistrationNumberIgnoreCase(registrationNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        TollPlaza plaza = plazas.findById(plazaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Toll plaza not found"));
        TollRate rate = rates.findByPlazaIdAndVehicleClassAndActiveTrue(plazaId, vehicle.getVehicleClass())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No active toll rate configured for this vehicle class"));
        Wallet wallet = wallets.findByVehicleId(vehicle.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FASTag wallet not found"));

        BigDecimal amount = rate.getOneWayAmount();
        TollTransaction tx = new TollTransaction();
        tx.setVehicle(vehicle);
        tx.setPlaza(plaza);
        tx.setLaneNumber(laneNumber);
        tx.setAmount(amount);
        tx.setTransactionTime(Instant.now());

        if (!wallet.isActive() || wallet.getBalance().compareTo(amount) < 0) {
            tx.setStatus(TransactionStatus.INSUFFICIENT_BALANCE);
            tx.setFailureReason("Insufficient or inactive FASTag wallet");
            return transactions.save(tx);
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setUpdatedAt(Instant.now());
        wallets.save(wallet);
        tx.setStatus(TransactionStatus.SUCCESS);
        return transactions.save(tx);
    }

    public List<TollTransaction> recent() {
        return transactions.findTop100ByOrderByTransactionTimeDesc();
    }
}
