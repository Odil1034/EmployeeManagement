package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.entity.work.WorkCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "positions")
@SQLDelete(sql = "UPDATE positions SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Position extends Auditable {

    @Column(nullable = false, unique = true)
    private String name; // WELDER, PAINTER, etc

    @Column(nullable = false)
    private String description; // qo‘shimcha izoh

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

    @OneToMany(mappedBy = "allowedPosition")
    private List<WorkCategory> allowedWorks; // Qilishi mumkin bo'lgan ishlar ro'yhati
}