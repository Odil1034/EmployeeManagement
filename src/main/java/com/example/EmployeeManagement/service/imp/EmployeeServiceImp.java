package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.config.SessionUser;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.EmployeeCreateDTO;
import com.example.EmployeeManagement.dto.request.EmployeeUpdateDTO;
import com.example.EmployeeManagement.dto.response.EmployeeResponseDTO;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.DepartmentMapper;
import com.example.EmployeeManagement.mapper.EmployeeMapper;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.service.DepartmentService;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.RoleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private final DepartmentService departmentService;
    private final EmployeeRepository repository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper mapper;
    private final SessionUser sessionUser;
    private final RoleService roleService;

    @Override
    public Response<EmployeeResponseDTO> create(EmployeeCreateDTO dto) {
        boolean b = repository.existsByPhoneNumber(dto.phoneNumber());
        boolean b1 = repository.existsByEmail(dto.email());
        if (b) {
            throw new RuntimeException("This phone number already exist");
        } else if (b1) {
            throw new RuntimeException("This email already exist");
        }
        Department department = departmentMapper.toEntity(departmentService.findById(dto.departmentId()).getData());
        Employee employee = mapper.fromCreate(dto);

        return Response.ok(mapper.toDTO(employee));
    }

    @Override
    public Response<EmployeeResponseDTO> update(Long id, EmployeeUpdateDTO dto) {
        Employee employee = find(id);
        if (!roleService.hasRole(sessionUser.getID(), "ADMIN")
                && !employee.getId().equals(sessionUser.getID())) {
            throw new AccessDeniedException("You cannot edit this employee");
        }

        mapper.fromUpdate(dto, employee);
        Employee save = repository.save(employee);
        return Response.ok(mapper.toDTO(save));

    }

    private Employee find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = {0}", id));
    }

    @Override
    public Response<Boolean> delete(@NotNull Long id) {
        boolean b = repository.existsById(id);

        repository.softDelete(id);

        return Response.ok(true);
    }

    @Override
    public Response<EmployeeResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    @Override
    public Response<List<EmployeeResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream()
                .map(mapper::toDTO)
                .toList());
    }

}
