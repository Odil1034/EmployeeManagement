package com.example.EmployeeManagement.entity.product;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.enums.SizeUnit;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal height;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal width;

    @Column(precision = 8, scale = 2)
    private BigDecimal length;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SizeUnit unit;
}