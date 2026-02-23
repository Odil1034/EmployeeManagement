package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.enums.LeaveStatus;
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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "leaves")
@SQLDelete(sql = "UPDATE leaves SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Leave extends Auditable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status; // PENDING, APPROVED, REJECTED

    @Column(length = 500)
    private String reason;
}
