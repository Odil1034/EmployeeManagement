package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.EmployeeCreateDTO;
import com.example.EmployeeManagement.dto.request.EmployeeUpdateDTO;
import com.example.EmployeeManagement.dto.response.EmployeeResponseDTO;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_PATH_V1 + "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Response<EmployeeResponseDTO>> create(@Valid @RequestBody EmployeeCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(dto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<EmployeeResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<EmployeeResponseDTO>> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response<EmployeeResponseDTO>> updateById(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDTO dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<EmployeeResponseDTO>>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

}
