package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "shifts")
@SQLDelete(sql = "UPDATE shifts SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Shift extends Auditable {

    @Column(nullable = false, unique = true)
    private String name; // MORNING, NIGHT

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Builder.Default
    @OneToMany(mappedBy = "shift", fetch = FetchType.EAGER)
    private Set<ShiftAssignment> shiftAssignments = new HashSet<>();
}

