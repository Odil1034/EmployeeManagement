package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkEmpUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkResponseDTO;
import com.example.EmployeeManagement.service.WorkService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/work")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<WorkResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<WorkResponseDTO>>> getAll() {
        return ResponseEntity.ok(workService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Response<WorkResponseDTO>> create(@Valid @RequestBody WorkCreateDTO dto) {
        return ResponseEntity.ok(workService.create(dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<WorkResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody WorkEmpUpdateDTO dto) {
        return ResponseEntity.ok(workService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<WorkResponseDTO>> delete(@PathVariable Long id){
        return ResponseEntity.ok(workService.delete(id));
    }
}
