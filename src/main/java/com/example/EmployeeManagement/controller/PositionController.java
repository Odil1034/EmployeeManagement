package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.PositionCreateDTO;
import com.example.EmployeeManagement.dto.request.PositionUpdateDTO;
import com.example.EmployeeManagement.dto.response.PositionResponseDTO;
import com.example.EmployeeManagement.service.PositionService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<PositionResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(positionService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<PositionResponseDTO>>> getAll() {
        return ResponseEntity.ok(positionService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Response<PositionResponseDTO>> create(@Valid @RequestBody PositionCreateDTO dto) {
        return ResponseEntity.ok(positionService.create(dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<PositionResponseDTO>> update(@PathVariable Long id,
                                                                @Valid @RequestBody PositionUpdateDTO dto) {
        return ResponseEntity.ok(positionService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<PositionResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(positionService.delete(id));
    }
}
