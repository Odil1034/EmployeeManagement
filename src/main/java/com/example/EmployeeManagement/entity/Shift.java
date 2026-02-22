package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.enums.ShiftType;
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
@Table(name = "shifts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "start_time", "end_time"})
        }
)
@SQLDelete(sql = "UPDATE shifts SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Shift extends Auditable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftType name; // MORNING, NIGHT, WEEKEND_SHIFT

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Builder.Default
    @Column(name = "grace_period_minutes", nullable = false)
    private Integer gracePeriodMinutes = 10;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ShiftAssignment> shiftAssignments = new HashSet<>();

    @Override
    public String toString() {
        return "Shift{" +
                "name=" + name +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", gracePeriodMinutes=" + gracePeriodMinutes +
                ", active=" + active +
                ", shiftAssignments=" + shiftAssignments +
                '}';
    }
}

