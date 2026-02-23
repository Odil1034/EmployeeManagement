package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.AttendanceCheckOutDTO;
import com.example.EmployeeManagement.dto.request.AttendanceCreateDTO;
import com.example.EmployeeManagement.dto.request.AttendanceFilterDTO;
import com.example.EmployeeManagement.dto.request.AttendanceUpdateDTO;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.dto.response.DashboardAttendanceDTO;
import com.example.EmployeeManagement.dto.response.MonthlyAttendanceSummaryDTO;
import com.example.EmployeeManagement.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService
        extends GenericCrudService<Long, Attendance, AttendanceResponseDTO, AttendanceCreateDTO, AttendanceUpdateDTO> {

    Response<AttendanceResponseDTO> approveAttendance(Long attendanceId);

    Response<AttendanceResponseDTO> checkOut(AttendanceCheckOutDTO dto);

    Response<List<AttendanceResponseDTO>> getDailyAttendance(LocalDate date);

    Response<List<AttendanceResponseDTO>> getDepartmentDailyAttendance(Long departmentId, LocalDate date);

    Response<MonthlyAttendanceSummaryDTO> getMonthlySummary(Long employeeId, int year, int month);

    Response<DashboardAttendanceDTO> getTodayDashboardAttendanceSummary();

    Response<List<AttendanceResponseDTO>> filterAttendance(AttendanceFilterDTO dto);
}
