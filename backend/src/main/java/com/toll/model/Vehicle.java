package com.toll.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "vehicles", indexes = {
    @Index(name = "idx_vehicle_registration", columnList = "registrationNumber", unique = true),
    @Index(name = "idx_vehicle_tag", columnList = "fastagId", unique = true)
})
@Getter @Setter @NoArgsConstructor
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @Column(nullable = false, unique = true, length = 40)
    private String fastagId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private VehicleClass vehicleClass;

    @Column(nullable = false, length = 100)
    private String ownerName;

    @Column(length = 20)
    private String ownerPhone;

    @Column(nullable = false)
    private boolean active = true;

    private Instant createdAt = Instant.now();
}
