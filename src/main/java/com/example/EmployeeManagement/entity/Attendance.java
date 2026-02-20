package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.example.EmployeeManagement.enums.AttendanceStatus;

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

    @OneToOne
    @JoinColumn(name = "shift_assignment_id", nullable = false, unique = true)
    private ShiftAssignment shiftAssignment;

    @Column(name = "check_in")
    private LocalTime checkIn;
    @Column(name = "check_out")
    private LocalTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Builder.Default
    @Column(name = "is_approved")
    private boolean isApproved = false;

}
