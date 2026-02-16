package com.example.EmployeeManagement.entity.work;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.SalaryPayment;
import com.example.EmployeeManagement.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "works",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "work_date", "title"})})
public class Work extends Auditable {
    // Work — real ishchi tomonidan bajarilgan ish
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "work_type_id", nullable = false)
    private WorkType workType;  // Размерга кесиш, Тешиш, Овал, Сайкаллаш

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate; // 07.08.2026

    @Column(nullable = false)
    private Integer quantity;   // 10 ta

    @Column(nullable = false)
    private BigDecimal price; // Boss override qilsa update qilinadi

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "work_status", nullable = false)
    private WorkStatus status = WorkStatus.PENDING; // PENDING, APPROVED, REJECTED

    @ManyToOne
    @JoinColumn(name = "salary_payment_id")
    private SalaryPayment salaryPayment;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Employee approvedBy; //     sex boshlig'i / ish boshqaruvchi
}
