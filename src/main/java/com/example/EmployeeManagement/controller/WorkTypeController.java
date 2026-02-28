package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkTypeCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkTypeUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkTypeResponseDTO;
import com.example.EmployeeManagement.service.WorkTypeService;
import com.example.EmployeeManagement.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/work-type")
@RequiredArgsConstructor
public class WorkTypeController {

    private final WorkTypeService workTypeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<WorkTypeResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workTypeService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<WorkTypeResponseDTO>>> getAll() {
        return ResponseEntity.ok(workTypeService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Response<WorkTypeResponseDTO>> create(@Valid @RequestBody WorkTypeCreateDTO dto) {
        return ResponseEntity.ok(workTypeService.create(dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<WorkTypeResponseDTO>> update(@PathVariable Long id,
                                                                @Valid @RequestBody WorkTypeUpdateDTO dto) {
        return ResponseEntity.ok(workTypeService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<WorkTypeResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(workTypeService.delete(id));
    }
}
