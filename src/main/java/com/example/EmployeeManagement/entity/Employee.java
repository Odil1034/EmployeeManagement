package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.entity.work.Work;
import com.example.EmployeeManagement.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "employees")
public class Employee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ("first_name"), nullable = false)
    private String firstName;

    @Column(name = ("last_name"))
    private String lastName;

    @Column(name = ("phone_number"), unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

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
}
