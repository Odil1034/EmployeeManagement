package com.example.EmployeeManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "all_users")
@SQLDelete(sql = "UPDATE all_users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class User extends Auditable {

    @NotNull(message = "username is required")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull(message = "password is required")
    @Column(nullable = false)
    private String password;

    @Email(message = "Email should be valid")
    @NotNull(message = "email is required")
    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must be +998XXXXXXXXX")
    private String phoneNumber;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @Column(name = "is_account_non_locked", nullable = false)
    private boolean isAccountNonLocked = true;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
