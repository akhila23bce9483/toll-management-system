package com.toll.config;

import com.toll.model.*;
import com.toll.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(TollPlazaRepository plazas, TollRateRepository rates,
                           VehicleRepository vehicles, WalletRepository wallets) {
        return args -> {
            if (plazas.count() > 0) return;

            TollPlaza plaza = new TollPlaza();
            plaza.setCode("PLZ-001");
            plaza.setName("National Highway Demo Plaza");
            plaza.setHighway("NH-16");
            plaza.setLocation("Andhra Pradesh");
            plaza.setLaneCount(8);
            plaza = plazas.save(plaza);

            for (VehicleClass type : VehicleClass.values()) {
                TollRate rate = new TollRate();
                rate.setPlaza(plaza);
                rate.setVehicleClass(type);
                rate.setOneWayAmount(amount(type));
                rate.setReturnAmount(amount(type).multiply(new BigDecimal("1.5")));
                rates.save(rate);
            }

            Vehicle vehicle = new Vehicle();
            vehicle.setRegistrationNumber("AP37AB1234");
            vehicle.setFastagId("TAG-DEMO-001");
            vehicle.setVehicleClass(VehicleClass.CAR);
            vehicle.setOwnerName("Demo Customer");
            vehicle.setOwnerPhone("9000000000");
            vehicle = vehicles.save(vehicle);

            Wallet wallet = new Wallet();
            wallet.setVehicle(vehicle);
            wallet.setBalance(new BigDecimal("1000.00"));
            wallets.save(wallet);
        };
    }

    private BigDecimal amount(VehicleClass type) {
        return switch (type) {
            case CAR -> new BigDecimal("100.00");
            case LIGHT_COMMERCIAL_VEHICLE -> new BigDecimal("160.00");
            case BUS -> new BigDecimal("220.00");
            case TRUCK -> new BigDecimal("260.00");
            case MULTI_AXLE_TRUCK -> new BigDecimal("420.00");
            case HEAVY_CONSTRUCTION_VEHICLE -> new BigDecimal("500.00");
        };
    }
}
