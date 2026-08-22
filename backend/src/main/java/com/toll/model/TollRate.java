package com.toll.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "toll_rates", uniqueConstraints = @UniqueConstraint(name = "uk_rate_plaza_class", columnNames = {"plaza_id", "vehicleClass"}))
@Getter @Setter @NoArgsConstructor
public class TollRate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plaza_id", nullable = false)
    private TollPlaza plaza;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private VehicleClass vehicleClass;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal oneWayAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal returnAmount;

    @Column(nullable = false)
    private boolean active = true;

    private Instant effectiveFrom = Instant.now();
}
