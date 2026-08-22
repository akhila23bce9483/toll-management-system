package com.toll.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "toll_transactions", indexes = {
    @Index(name = "idx_txn_time", columnList = "transactionTime"),
    @Index(name = "idx_txn_vehicle", columnList = "vehicle_id"),
    @Index(name = "idx_txn_reference", columnList = "reference", unique = true)
})
@Getter @Setter @NoArgsConstructor
public class TollTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String reference = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plaza_id", nullable = false)
    private TollPlaza plaza;

    @Column(nullable = false, length = 20)
    private String laneNumber;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionStatus status;

    @Column(nullable = false)
    private Instant transactionTime = Instant.now();

    @Column(length = 500)
    private String failureReason;
}
