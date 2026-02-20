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
import jakarta.transaction.Transactional;
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
    @Transactional
    public Response<EmployeeResponseDTO> create(EmployeeCreateDTO dto) {
        if (repository.existsByPhoneNumber(dto.phoneNumber())) {
            throw new RuntimeException("This phone number already exists");
        }
        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("This email already exists");
        }
        Department department = departmentService.find(dto.departmentId());

        Employee employee = mapper.fromCreate(dto);
        employee.setDepartment(department);

        Employee savedEmployee = repository.save(employee);

        return Response.ok(mapper.toDTO(savedEmployee));
    }

    @Override
    public Response<EmployeeResponseDTO> update(Long id, EmployeeUpdateDTO dto) {
        Employee employee = find(id);
        /*if (!roleService.hasRole(sessionUser.getID(), "ADMIN")
                && !employee.getId().equals(sessionUser.getID())) {
            throw new AccessDeniedException("You cannot edit this employee");
        }*/

        mapper.fromUpdate(dto, employee);
        Employee save = repository.save(employee);
        return Response.ok(mapper.toDTO(save));

    }

    private Employee find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = {0}", id));
    }

    @Override
    @Transactional
    public Response<EmployeeResponseDTO> delete(@NotNull Long id) {
        Employee employee = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found or already deleted with id: " + id));

        employee.setDeleted(true);
        Employee save = repository.save(employee);

        return Response.ok(mapper.toDTO(save));
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
