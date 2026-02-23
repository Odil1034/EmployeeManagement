package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$") @NotBlank String email);

    @Modifying
    @Query("UPDATE Employee p SET p.deleted = TRUE WHERE p.id = ?1")
    int softDelete(Long id);

    Optional<Employee> findByIdAndDeletedFalse(@NotNull Long id);

    Optional<Employee> findByUserId(Long userId);

    @Query("SELECT e FROM Employee e WHERE e.deleted = false")
    List<Employee> findAllCustom();


    /*@Query("SELECT p FROM Employee p WHERE p.access = ?1 AND p.deleted = FALSE")
    Optional<Employee> findByFirstName(String firstName);

    @Query("SELECT p FROM Employee p WHERE p.id = ?1 AND p.deleted = FALSE")
    Optional<Employee> findActiveById(Long id);

    @Query("SELECT p FROM Employee p WHERE p.deleted = FALSE")
    List<Employee> findAllActive();



    boolean existsByAccess(String access);

    boolean existsByAccessAndIdNot(String access, Long id);

    @Query(value = """
            SELECT p.*
            FROM permissions p
            JOIN role_permission rp ON p.id = rp.permission_id
            JOIN user_roles ur ON rp.role_id = ur.role_id
            JOIN users u ON ur.user_id = u.id
            WHERE u.username = :username
        """, nativeQuery = true)
    Set<Employee> findEmployeesByUsername(@Param("username") String username);*/
}
