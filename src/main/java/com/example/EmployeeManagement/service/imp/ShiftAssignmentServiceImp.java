package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftAssignmentResponseDTO;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.Shift;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.ShiftAssignmentMapper;
import com.example.EmployeeManagement.repository.ShiftAssignmentRepository;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.ShiftAssignmentService;
import com.example.EmployeeManagement.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftAssignmentServiceImp implements ShiftAssignmentService {

    private final ShiftService shiftService;
    private final EmployeeService employeeService;
    private final ShiftAssignmentMapper mapper;
    private final ShiftAssignmentRepository repository;

    @Override
    public Response<ShiftAssignmentResponseDTO> create(ShiftAssignmentCreateDTO dto) {
        // Duplicate check
        if (repository.existsByEmployeeIdAndWorkDateAndActiveTrue(dto.employeeId(), dto.workDate())) {
            throw new RuntimeException("ShiftAssignment already exists for this employee and date");
        }
        Shift shift = shiftService.find(dto.shiftId());
        Employee employee = employeeService.find(dto.employeeId());
        ShiftAssignment shiftAssignment = mapper.fromCreate(dto);
        shiftAssignment.setEmployee(employee);
        shiftAssignment.setShift(shift);

        ShiftAssignment save = repository.save(shiftAssignment);
        return Response.ok(HttpStatus.CREATED, mapper.toDTO(save));
    }

    @Override
    public Response<ShiftAssignmentResponseDTO> update(Long id, ShiftAssignmentUpdateDTO dto) {
        ShiftAssignment sa = find(id);
        mapper.fromUpdate(dto, sa);
        if (dto.employeeId() != null) {
            sa.setEmployee(employeeService.find(dto.employeeId()));
        }
        if (dto.shiftId() != null) {
            sa.setShift(shiftService.find(dto.shiftId()));
        }

        ShiftAssignment save = repository.save(sa);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<ShiftAssignmentResponseDTO> delete(Long id) {
        ShiftAssignment sa = find(id);
        sa.setActive(false);
        sa.setDeleted(true);
        repository.save(sa);
        return Response.ok(mapper.toDTO(sa));
    }

    @Override
    public Response<ShiftAssignmentResponseDTO> findById(Long id) {
        ShiftAssignment sa = find(id);
        return Response.ok(mapper.toDTO(sa));
    }

    @Override
    public ShiftAssignment find(Long id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShiftAssignment not found with id: {0}", id));
    }

    @Override
    public Response<List<ShiftAssignmentResponseDTO>> findByEmployeeId(Long employeeId) {
        List<ShiftAssignment> byEmployeeId = repository.findByEmployeeId(employeeId);
        List<ShiftAssignmentResponseDTO> list = byEmployeeId.stream().map(mapper::toDTO).toList();
        return Response.ok(list);
    }

    @Override
    public Response<List<ShiftAssignmentResponseDTO>> findAll() {
        List<ShiftAssignmentResponseDTO> list = repository.findAllByActiveTrue()
                .stream().map(mapper::toDTO).toList();
        return Response.ok(list);
    }

    // GET by Employee FullName
    public Response<List<ShiftAssignmentResponseDTO>> getByEmployeeFullName(String fullName) {
        List<ShiftAssignmentResponseDTO> list = repository
                .findAllByEmployeeFullName(fullName)
                .stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(list);
    }

    @Override
    public Response<List<ShiftAssignmentResponseDTO>> findByDate(LocalDate date) {
        List<ShiftAssignmentResponseDTO> list = repository
                .findAllByWorkDateAndActiveTrue(date)
                .stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(list);
    }

    @Override
    public Response<List<ShiftAssignmentResponseDTO>> findByShiftId(Long shiftId) {
        List<ShiftAssignmentResponseDTO> list = repository
                .findAllByShiftIdAndActiveTrue(shiftId)
                .stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(list);
    }

    @Override
    public Response<List<ShiftAssignmentResponseDTO>> findAllByDepartmentId(Long departmentId) {
        List<ShiftAssignmentResponseDTO> list = repository
                .findAllByDepartmentId(departmentId)
                .stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(list);
    }

    @Override
    public Response<List<ShiftAssignmentResponseDTO>> findByShiftName(String name) {
        List<ShiftAssignmentResponseDTO> list = repository
                .findAllByShiftName(name) // yoki native query versiyasi
                .stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(list);
    }
}
