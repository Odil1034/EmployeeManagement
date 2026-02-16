package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.example.EmployeeManagement.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Baxriddinov Odiljon
 * @since 07/02/2026 20:27
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendances")
@SuperBuilder(toBuilder = true)
public class Attendance extends Auditable {
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDate date;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private Long approvedBy;

    private LocalDateTime approvedAt;

    private boolean isApproved;

}
