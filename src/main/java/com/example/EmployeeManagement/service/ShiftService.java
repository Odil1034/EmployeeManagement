package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.ActivateShiftDTO;
import com.example.EmployeeManagement.dto.request.ShiftCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftHistoryDTO;
import com.example.EmployeeManagement.dto.response.ShiftResponseDTO;
import com.example.EmployeeManagement.entity.Shift;
import com.example.EmployeeManagement.enums.ShiftType;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShiftService
        extends GenericCrudService<Long, Shift, ShiftResponseDTO, ShiftCreateDTO, ShiftUpdateDTO> {

    Response<ShiftResponseDTO> activate(Long id, @Valid ActivateShiftDTO dto);

    Response<ShiftHistoryDTO> shiftHistory(Long id);

    Response<List<ShiftResponseDTO>> filter(String name, Boolean active, LocalTime startTime, Pageable pageable);

    Response<List<ShiftResponseDTO>> availableShifts();


    Response<List<ShiftResponseDTO>> bulkCreate(@Valid List<ShiftCreateDTO> bulkShifts);


    Response<ShiftResponseDTO> duplicateCheck(ShiftType name, LocalTime startTime, LocalTime endTime);

    Shift find(Long id);
}
