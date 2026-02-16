package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "permissions")
public class Permission extends Auditable {

    private String access;
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "permissions") // Role bilan bog‘langan
    private Set<Role> roles = new HashSet<>();

}
