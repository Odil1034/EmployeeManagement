package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.AttendanceCreateDTO;
import com.example.EmployeeManagement.dto.request.AttendanceUpdateDTO;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.service.AttendanceService;

import java.util.List;

public class AttendanceServiceImp implements AttendanceService {

    @Override
    public Response<AttendanceResponseDTO> create(AttendanceCreateDTO dto) {
        return null;
    }

    @Override
    public Response<AttendanceResponseDTO> update(Long aLong, AttendanceUpdateDTO dto) {
        return null;
    }

    @Override
    public Response<AttendanceResponseDTO> delete(Long aLong) {
        return null;
    }

    @Override
    public Response<AttendanceResponseDTO> findById(Long aLong) {
        return null;
    }

    @Override
    public Response<List<AttendanceResponseDTO>> findAll() {
        return null;
    }

    @Override
    public AttendanceResponseDTO approveAttendance(Long attendanceId, Long approverId) {
        return null;
    }
}
