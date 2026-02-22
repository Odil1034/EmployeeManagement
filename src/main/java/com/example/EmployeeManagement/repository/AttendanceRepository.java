package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {


}
