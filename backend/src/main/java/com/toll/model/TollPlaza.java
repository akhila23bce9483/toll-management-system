package com.toll.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "toll_plazas")
@Getter @Setter @NoArgsConstructor
public class TollPlaza {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 120)
    private String highway;

    @Column(nullable = false, length = 120)
    private String location;

    @Column(nullable = false)
    private Integer laneCount = 2;

    @Column(nullable = false)
    private boolean active = true;

    private Instant createdAt = Instant.now();
}
