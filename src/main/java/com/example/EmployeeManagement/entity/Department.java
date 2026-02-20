package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.enums.DepartmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "departments")
@SQLDelete(sql = "UPDATE documents SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Department extends Auditable {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DepartmentStatus status = DepartmentStatus.ACTIVE;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
