package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.config.SessionUser;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.EmployeeCreateDTO;
import com.example.EmployeeManagement.dto.request.EmployeeUpdateDTO;
import com.example.EmployeeManagement.dto.response.EmployeeResponseDTO;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.DepartmentMapper;
import com.example.EmployeeManagement.mapper.EmployeeMapper;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.service.DepartmentService;
import com.example.EmployeeManagement.service.EmailService;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.RoleService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private final DepartmentService departmentService;
    private final EmployeeRepository repository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final SessionUser sessionUser;
    private final RoleService roleService;

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

        try {
            Employee savedEmployee = repository.save(employee);

            String rawPassword = generateRandomPassword(10);
            User user = User.builder()
                    .username(generateUsername(dto))
                    .password(passwordEncoder.encode(rawPassword))
                    .phoneNumber(dto.phoneNumber())
                    .email(dto.email())
                    .isAccountNonLocked(true)
                    .build();

            // 🔹 Bi-directional link
            linkEmployeeAndUser(savedEmployee, user);

            // 🔹 Save user
            userRepository.save(user);

            // 🔹 Email
            if (dto.email() != null && !dto.email().isBlank()) {
                emailService.sendEmail(dto.email(),
                        "Your System Credentials",
                        String.format(
                                "<p>Dear %s %s,</p>" +
                                        "<p>Welcome to our %s company</p>" +
                                        "<p>Your account has been created.</p>" +
                                        "<p><b>Username:</b> %s<br/>" +
                                        "<b>Password:</b> %s</p>" +
                                        "<p>Please change your password after first login.</p>",
                                employee.getFirstName(),
                                employee.getLastName(),
                                department.getName(),
                                user.getUsername(),
                                rawPassword
                        )
                );
            }

            return Response.ok(mapper.toDTO(savedEmployee));

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Employee or User already exists. Please try again.");
        }
    }

    public void linkEmployeeAndUser(Employee employee, User user) {
        user.setEmployee(employee);
        employee.setUser(user);
    }

    // Random password generator
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }

    // Username generator
    private String generateUsername(EmployeeCreateDTO dto) {
        String base = dto.firstName().toLowerCase() + "." + dto.lastName().toLowerCase();
        String randomDigits = String.valueOf((int) (Math.random() * 1000));
        return base + randomDigits;
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

    @Override
    public Employee find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id = {0}", id));
    }

    @Override
    public Employee findByUserId(Long userID) {
        return repository.findByUserId(userID)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
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

    @Override
    public List<Employee> findAllCustom() {
        return repository.findAllCustom();
    }
}
