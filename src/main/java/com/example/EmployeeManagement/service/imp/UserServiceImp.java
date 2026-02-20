package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.config.SessionUser;
import com.example.EmployeeManagement.dto.ErrorResponse;
import com.example.EmployeeManagement.dto.request.PasswordUpdateDTO;
import com.example.EmployeeManagement.dto.request.UserUpdateDTO;
import com.example.EmployeeManagement.dto.request.UserCreateDTO;
import com.example.EmployeeManagement.dto.response.UserResponseDTO;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.exception.UserNotFoundException;
import com.example.EmployeeManagement.mapper.UserMapper;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final SessionUser sessionUser;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Response<UserResponseDTO> profile() {
        Long id = sessionUser.getID();
        User user = find(id);
        return Response.ok(mapper.toDTO(user));
    }

    @Override
    public Optional<User> findByUsername(@NotNull String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Response<UserResponseDTO> create(UserCreateDTO dto) {
        final var user = mapper.fromCreate(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final var newUser = repository.save(user);
        return Response.ok(mapper.toDTO(newUser));
    }

    @Transactional
    @Override
    public Response<UserResponseDTO> update(Long id, UserUpdateDTO dto) {
        User user = find(id);
        mapper.fromUpdate(dto, user);
        return Response.ok(mapper.toDTO(user));
    }

    @Override
    public Response<UserResponseDTO> delete(Long id) {
        User user = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or already deleted with id: " + id));

        user.setDeleted(true);
        User save = repository.save(user);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<UserResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    public User find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: {0} not found", id));
    }

    @Override
    public Response<List<UserResponseDTO>> findAll() {
        return Response.ok(repository.findAllCustom().stream()
                .map(mapper::toDTO)
                .toList());
    }

    @Override
    public Response<String> updatePassword(PasswordUpdateDTO dto) {
        User user = repository.findByIdCustom(sessionUser.getID())
                .orElseThrow(() -> new UserNotFoundException("User not found with session id: {0}", sessionUser.getID()));
        if (passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.newPassword()));
            final var newUser = repository.save(user);
            return Response.ok("Password successfully updated");
        }
        return Response.error(HttpStatus.BAD_REQUEST,
                ErrorResponse.of("400", "Password does not match", ""));
    }

}
