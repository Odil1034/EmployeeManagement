package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.enums.CheckInStatus;
import com.example.EmployeeManagement.enums.CheckOutStatus;
import jakarta.persistence.*;
import lombok.*;
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
@SuperBuilder(toBuilder = true)
@Table(
        name = "attendances",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "shift_assignment_id", "date"})
        },
        indexes = {
                @Index(name = "idx_attendance_employee_date", columnList = "employee_id, date"),
                @Index(name = "idx_attendance_date_status", columnList = "date, status"),
                @Index(name = "idx_attendance_shiftassignment_date", columnList = "shift_assignment_id, date")
        }
)
public class Attendance extends Auditable {

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "check_in")
    private LocalTime checkIn;

    @Column(name = "check_out")
    private LocalTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "check_in_status")
    private CheckInStatus checkInStatus = CheckInStatus.ABSENT;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "check_out_status")
    private CheckOutStatus checkOutStatus = CheckOutStatus.NO_CHECKOUT;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Builder.Default
    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_assignment_id", nullable = false)
    private ShiftAssignment shiftAssignment;
}
