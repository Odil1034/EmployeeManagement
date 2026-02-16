package com.example.EmployeeManagement.entity.work;

import com.example.EmployeeManagement.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "work_types")
public class WorkType extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Размерга кесиш, Тешиш, Овал, Сайкаллаш

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private WorkCategory category;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal defaultPrice;

    @OneToMany(mappedBy = "workType", cascade = CascadeType.ALL)
    private List<Work> works;
}