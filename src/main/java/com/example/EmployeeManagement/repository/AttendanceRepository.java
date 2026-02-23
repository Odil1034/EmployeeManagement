package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.dto.response.MonthlyAttendanceSummaryDTO;
import com.example.EmployeeManagement.entity.Attendance;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import com.example.EmployeeManagement.enums.AttendanceStatus;
import com.example.EmployeeManagement.enums.CheckInStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByEmployeeAndShiftAssignmentAndDate(Employee employee, ShiftAssignment shiftAssignment, LocalDate now);

    Optional<Attendance> findByEmployeeAndShiftAssignmentAndDate(Employee employee, ShiftAssignment shiftAssignment, LocalDate today);

    @Query("SELECT a FROM Attendance a WHERE a.id = :id AND a.deleted = false")
    Optional<Attendance> findByIdCustom(Long id);

    List<Attendance> findAllByDeletedFalse();

    @Query("""
                SELECT a FROM Attendance a
                WHERE a.employee.department.id = :departmentId
                AND a.date = :date
                AND a.deleted = false
            """)
    List<Attendance> findAllByDepartmentAndDate(Long departmentId, LocalDate date);

    List<Attendance> findAllByEmployeeAndDateAndDeletedFalse(Employee employee, LocalDate date);

    long countByDateAndStatus(LocalDate today, AttendanceStatus attendanceStatus);

    long countByDateAndCheckInStatus(LocalDate today, CheckInStatus checkInStatus);

    @Query("""
                SELECT new com.example.EmployeeManagement.dto.response.MonthlyAttendanceSummaryDTO(
                    COUNT(a),
                    SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
                    SUM(CASE WHEN a.checkInStatus = 'LATE' THEN 1 ELSE 0 END),
                    SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END)
                )
                FROM Attendance a
                WHERE a.employee.id = :employeeId
                AND a.date BETWEEN :start AND :end
                AND a.deleted = false
            """)
    MonthlyAttendanceSummaryDTO getMonthlySummary(@Param("employeeId") Long employeeId,
                                                  @Param("start") LocalDate start,
                                                  @Param("end") LocalDate end);

    @Query("""
                SELECT a FROM Attendance a
                WHERE (:employeeId IS NULL OR a.employee.id = :employeeId)
                  AND (:departmentId IS NULL OR a.employee.department.id = :departmentId)
                  AND (:status IS NULL OR a.status = :status)
                  AND (:checkInStatus IS NULL OR a.checkInStatus = :checkInStatus)
                  AND a.date BETWEEN :from AND :to
                  AND a.deleted = false
            """)
    List<Attendance> filterAttendance(
            @Param("employeeId") Long employeeId,
            @Param("departmentId") Long departmentId,
            @Param("status") AttendanceStatus status,
            @Param("checkInStatus") CheckInStatus checkInStatus,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    boolean existsByEmployeeAndDateAndDeletedFalse(Employee employee, LocalDate today);
}
