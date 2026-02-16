package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.entity.work.Work;
import com.example.EmployeeManagement.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "salary_payments")
@SQLDelete(sql = "UPDATE salary_payments SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class SalaryPayment extends Auditable {

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate periodStart; // oylik period boshlanishi

    @Column(nullable = false)
    private LocalDate periodEnd;   // oylik period tugashi

    @Column(nullable = false)
    private BigDecimal totalAmount; // jami to‘lov summasi

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING; // PENDING, PAID, REJECTED

    @OneToMany(mappedBy = "salaryPayment")
    private List<Work> works; // bu paymentga kirgan ishlar
}
