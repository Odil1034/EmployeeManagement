package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Shift;
import com.example.EmployeeManagement.enums.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift, Long>, JpaSpecificationExecutor<Shift> {

    // Duplicate check
    boolean existsByNameAndStartTimeAndEndTime(ShiftType name, LocalTime startTime, LocalTime endTime);

    boolean existsByNameAndStartTimeAndEndTimeAndIdNot(ShiftType name, LocalTime startTime, LocalTime endTime, Long id);

    // Custom find by id where not deleted and active
    @Query("SELECT sh FROM Shift sh WHERE sh.id = :id AND sh.deleted = false AND sh.active = true")
    Optional<Shift> findByIdCustom(@Param("id") Long id);

    // Get all active and not deleted shifts
    @Query("SELECT sh FROM Shift sh WHERE sh.deleted = FALSE AND sh.active = TRUE")
    List<Shift> findAllCustom();

    // Standard JPA method
    Optional<Shift> findByIdAndDeletedFalse(Long id);

    // Active shifts (date argument olib tashlandi)
    List<Shift> findByActiveTrue();

    // Duplicate check for DTO usage
    Optional<Shift> findByNameAndStartTimeAndEndTime(String name, LocalTime startTime, LocalTime endTime);
}