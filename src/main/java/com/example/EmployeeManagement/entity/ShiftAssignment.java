package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE shift_assignments SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "shift_assignments",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"employee_id", "work_date"}
                )
        }
)
public class ShiftAssignment extends Auditable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Builder.Default
    @Column(name = "active")
    private boolean active = true;
}
