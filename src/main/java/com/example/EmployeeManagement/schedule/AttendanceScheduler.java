package com.example.EmployeeManagement.schedule;

import com.example.EmployeeManagement.entity.Attendance;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.enums.AttendanceStatus;
import com.example.EmployeeManagement.enums.CheckInStatus;
import com.example.EmployeeManagement.enums.CheckOutStatus;
import com.example.EmployeeManagement.repository.AttendanceRepository;
import com.example.EmployeeManagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class AttendanceScheduler {

    private final AttendanceRepository repository;
    private final EmployeeService employeeService;

    @Scheduled(cron = "0 59 23 * * *") // har kuni 23:59
    public void markAbsent() {

        LocalDate today = LocalDate.now();

        // Barcha employee
        List<Employee> employees = employeeService.findAllCustom();

        for (Employee employee : employees) {
            boolean exists = repository.existsByEmployeeAndDateAndDeletedFalse(employee, today);

            if (!exists) {
                // Attendance yaratish va ABSENT qo‘yish
                Attendance absent = Attendance.builder()
                        .employee(employee)
                        .status(AttendanceStatus.ABSENT)
                        .checkInStatus(CheckInStatus.ABSENT)
                        .checkOutStatus(CheckOutStatus.NO_CHECKOUT)
                        .date(today)
                        .isApproved(false)
                        .build();

                repository.save(absent);
            }
        }
    }
}