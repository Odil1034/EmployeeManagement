package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE shifts_assignments SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "shifts_assignments",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"employee_id", "work_date"}
                )
        }
)
public class ShiftAssignment extends Auditable {

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Column(name = "work_date")
    private LocalDate workDate;

    @OneToOne(mappedBy = "shiftAssignment", cascade = CascadeType.ALL)
    private Attendance attendance;
}
