package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.entity.work.Work;
import com.example.EmployeeManagement.enums.EmployeeRole;
import com.example.EmployeeManagement.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "employees")
@SQLDelete(sql = "UPDATE employees SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Employee extends Auditable {

    @NotNull(message = "firstName is required")
    @Column(name = ("first_name"), nullable = false)
    private String firstName;

    @Column(name = ("last_name"))
    private String lastName;

    @NotNull(message = "phoneNumber is required")
    @Column(name = ("phone_number"), unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position; // bitta employee → bitta position

    @Builder.Default
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Work> works = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "document_id")
    private Document profileImage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole = EmployeeRole.EMPLOYEE;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder.Default
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<ShiftAssignment> shiftAssignments = new HashSet<>();
}
