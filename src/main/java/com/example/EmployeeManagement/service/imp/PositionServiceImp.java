package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.PositionCreateDTO;
import com.example.EmployeeManagement.dto.request.PositionUpdateDTO;
import com.example.EmployeeManagement.dto.response.PositionResponseDTO;
import com.example.EmployeeManagement.dto.response.ShiftAssignmentResponseDTO;
import com.example.EmployeeManagement.dto.response.SimpleEmployeeDTO;
import com.example.EmployeeManagement.entity.Position;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.PositionMapper;
import com.example.EmployeeManagement.repository.PositionRepository;
import com.example.EmployeeManagement.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImp implements PositionService {

    private final PositionRepository repository;
    private final PositionMapper mapper;

    @Override
    public Response<PositionResponseDTO> create(PositionCreateDTO dto) {
        boolean b = repository.existsByName(dto.name());
        if (b) {
            throw new RuntimeException("Position already exists");
        }
        Position position = mapper.fromCreate(dto);
        Position save = repository.save(position);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<PositionResponseDTO> update(Long id, PositionUpdateDTO dto) {
        Position position = find(id);
        mapper.fromUpdate(dto, position);
        /*if (dto.employeeId() != null) {
            sa.setEmployee(employeeService.find(dto.employeeId()));
        }
        if (dto.shiftId() != null) {
            sa.setShift(shiftService.find(dto.shiftId()));
        }*/

        Position save = repository.save(position);
        return Response.ok(mapper.toDTO(save));
    }

    public Position find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id: {0}", id));
    }

    @Override
    public Response<PositionResponseDTO> delete(Long id) {
        Position p = find(id);
        p.setDeleted(true);
        repository.save(p);
        return Response.ok(mapper.toDTO(p));
    }

    @Override
    public Response<PositionResponseDTO> findById(Long id) {
        Position position = find(id);
        return Response.ok(mapper.toDTO(position));
    }

    @Override
    public Response<List<PositionResponseDTO>> findAll() {
        List<PositionResponseDTO> list = repository.findAllCustom()
                .stream().map(mapper::toDTO).toList();
        return Response.ok(list);
    }

    @Override
    public List<PositionResponseDTO> getPositionsByDepartment(Long departmentId) {
        return List.of();
    }

    @Override
    public void assignWorkCategories(Long positionId, List<Long> workCategoryIds) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public List<SimpleEmployeeDTO> getEmployees(Long positionId) {
        return List.of();
    }

    @Override
    public List<PositionResponseDTO> search(String keyword) {
        return List.of();
    }
}
