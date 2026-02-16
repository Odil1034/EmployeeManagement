package com.example.EmployeeManagement.entity.work;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.entity.Position;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "work_categories")
public class WorkCategory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Yog‘ochga ishlov berish, MDFga ishlov berish, Temir mahsulotlari

    @Column(length = 2000)
    private String description; // texnika xavfsizlik yoki guideline(ishni bajarish tartibi)

    @ManyToOne(fetch = FetchType.EAGER)
    private Position allowedPosition; // WELDER, PAINTER, etc

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<WorkType> workTypes;
}
