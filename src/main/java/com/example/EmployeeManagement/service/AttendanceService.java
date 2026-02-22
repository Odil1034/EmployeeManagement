package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.request.AttendanceCreateDTO;
import com.example.EmployeeManagement.dto.request.AttendanceUpdateDTO;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.entity.Attendance;

public interface AttendanceService
        extends GenericCrudService<Long, Attendance, AttendanceResponseDTO, AttendanceCreateDTO, AttendanceUpdateDTO> {

    AttendanceResponseDTO approveAttendance(Long attendanceId, Long approverId);
}
