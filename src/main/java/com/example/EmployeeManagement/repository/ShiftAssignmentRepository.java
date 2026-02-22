package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.dto.response.ShiftAssignmentResponseDTO;
import com.example.EmployeeManagement.entity.Shift;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {

    @Query("SELECT s FROM Shift s WHERE s.active = true AND s.deleted = false AND s.id NOT IN " +
            "(SELECT sa.shift.id FROM ShiftAssignment sa WHERE sa.workDate = :date)")
    List<Shift> findAvailableShifts(@Param("date") LocalDate date);


    boolean existsByEmployeeIdAndWorkDateAndActiveTrue(Long employeeId, LocalDate workDate);

    Optional<ShiftAssignment> findByIdAndActiveTrue(Long id);

    List<ShiftAssignment> findAllByActiveTrue();

    @Query(
            value = "SELECT sa.* FROM shift_assignments sa " +
                    "JOIN employees e ON sa.employee_id = e.id " +
                    "WHERE CONCAT(e.first_name, ' ', e.last_name) ILIKE %:fullName% " +
                    "AND sa.active = true",
            nativeQuery = true
    )
    List<ShiftAssignment> findAllByEmployeeFullName(@Param("fullName") String fullName);

    @Query(value = "SELECT * FROM shift_assignments WHERE employee_id = :employeeId AND active = true", nativeQuery = true)
    List<ShiftAssignment> findByEmployeeId(@Param("employeeId") Long employeeId);

    List<ShiftAssignment> findAllByWorkDateAndActiveTrue(LocalDate date);

    List<ShiftAssignment> findAllByShiftIdAndActiveTrue(Long shiftId);

    @Query(
            value = "SELECT sa.* " +
                    "FROM shift_assignments sa " +
                    "JOIN employees e ON sa.employee_id = e.id " +
                    "WHERE e.department_id = :departmentId " +
                    "AND sa.active = true",
            nativeQuery = true
    )
    List<ShiftAssignment> findAllByDepartmentId(@Param("departmentId") Long departmentId);

    @Query(
            value = "SELECT sa.* " +
                    "FROM shift_assignments sa " +
                    "JOIN shifts s ON sa.shift_id = s.id " +
                    "WHERE UPPER(s.name) = UPPER(:name) " +
                    "AND sa.active = true",
            nativeQuery = true
    )
    List<ShiftAssignment> findAllByShiftName(@Param("name") String name);
}
