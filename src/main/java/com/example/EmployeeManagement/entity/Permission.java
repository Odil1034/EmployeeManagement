package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "permissions")
@SQLDelete(sql = "UPDATE permissions SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Permission extends Auditable {

    @Column(nullable = false, unique = true)
    private String access;

    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "permissions") // Role bilan bog‘langan
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(access, that.access);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(access);
    }
}
