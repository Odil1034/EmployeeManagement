package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Shift;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShiftAssignmentsRepository extends JpaRepository<ShiftAssignment, Long> {

    @Query("SELECT s FROM Shift s WHERE s.active = true AND s.deleted = false AND s.id NOT IN " +
            "(SELECT sa.shift.id FROM ShiftAssignment sa WHERE sa.workDate = :date)")
    List<Shift> findAvailableShifts(@Param("date") LocalDate date);
}
