package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "all_users")
@SQLDelete(sql = "UPDATE all_users SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class User extends Auditable {

    @NotNull(message = "username is required")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "password is required")
    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;
}
