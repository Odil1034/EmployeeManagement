package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftAssignmentResponseDTO;
import com.example.EmployeeManagement.service.ShiftAssignmentService;
import com.example.EmployeeManagement.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/shift-assignments")
@RequiredArgsConstructor
public class ShiftAssignmentController {

    private final ShiftAssignmentService service;

    @PostMapping("/create")
    public ResponseEntity<Response<ShiftAssignmentResponseDTO>> create(@Valid @RequestBody ShiftAssignmentCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<ShiftAssignmentResponseDTO>> update(@PathVariable Long id,
                                                                       @Valid @RequestBody ShiftAssignmentUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<ShiftAssignmentResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.findByEmployeeId(employeeId));
    }

    @GetMapping("/by-employee-fullname")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getByEmployeeFullName(
            @Schema(type = "string", example = "FirstName LastName")
            @RequestParam String fullName) {
        return ResponseEntity.ok(service.getByEmployeeFullName(fullName));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<ShiftAssignmentResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/by-date")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getByDate(
            @Schema(type = "string", example = "21-03-2004")
            @RequestParam String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        return ResponseEntity.ok(service.findByDate(localDate));
    }

    @GetMapping("/by-shift")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getByShiftId(@RequestParam Long shiftId) {
        return ResponseEntity.ok(service.findByShiftId(shiftId));
    }

    @GetMapping("/by-department")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getByDepartment(@RequestParam Long departmentId) {
        return ResponseEntity.ok(service.findAllByDepartmentId(departmentId));
    }

    @GetMapping("/by-shift-name")
    public ResponseEntity<Response<List<ShiftAssignmentResponseDTO>>> getByShiftName(
            @RequestParam String name) {
        return ResponseEntity.ok(service.findByShiftName(name));
    }
}
