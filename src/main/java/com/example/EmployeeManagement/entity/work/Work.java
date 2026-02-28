package com.example.EmployeeManagement.entity.work;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.SalaryPayment;
import com.example.EmployeeManagement.entity.product.Product;
import com.example.EmployeeManagement.entity.product.ProductVariant;
import com.example.EmployeeManagement.enums.WorkStatus;
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
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "works",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "work_date", "work_type_id"})})

@SQLDelete(sql = "UPDATE works SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Work extends Auditable {
    // Work — real ishchi tomonidan bajarilgan ish

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_type_id", nullable = false)
    private WorkType workType;  // Размерга кесиш, Тешиш, Овал, Сайкаллаш

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate; // 07.08.2026

    @Column(nullable = false)
    private Integer quantity; // 10 ta

    @ManyToMany
    @JoinTable(
            name = "work_product_variant",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "product_variant_id")
    )
    private List<ProductVariant> productVariants;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;   // Boss override qilsa update qilinadi

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "work_status", nullable = false)
    private WorkStatus status = WorkStatus.PENDING; // PENDING, APPROVED, REJECTED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_payment_id")
    private SalaryPayment salaryPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;    // sex boshlig'i / ish boshqaruvchi
}
