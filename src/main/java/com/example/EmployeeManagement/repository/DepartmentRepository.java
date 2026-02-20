package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.enums.DepartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT p FROM Department p WHERE p.name = ?1 AND p.deleted = FALSE")
    Optional<Department> findByName(String name);

    @Modifying
    @Query("UPDATE Department p SET p.deleted = true WHERE p.id = :id")
    int softDelete(@Param("id") Long id);

    boolean existsByName(String name);

    @Query("SELECT d FROM Department d WHERE d.deleted = FALSE")
    Set<Department> findAllActive();
    @Modifying
    @Query(value = "UPDATE departments SET status = :status WHERE id = :id", nativeQuery = true)
    int updateStatus(@Param("id") Long id,
                     @Param("status") String status);

    Optional<Department> findByIdAndDeletedFalse(Long id);
}
