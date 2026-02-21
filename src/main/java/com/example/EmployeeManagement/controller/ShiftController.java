package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.ActivateShiftDTO;
import com.example.EmployeeManagement.dto.request.ShiftCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftHistoryDTO;
import com.example.EmployeeManagement.dto.response.ShiftResponseDTO;
import com.example.EmployeeManagement.service.ShiftService;
import com.example.EmployeeManagement.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_PATH_V1 + "/shift")
public class ShiftController {

    private final ShiftService shiftService;

    // ✅ Create shift
    @PostMapping("/create")
    public ResponseEntity<Response<ShiftResponseDTO>> create(@Valid @RequestBody ShiftCreateDTO dto) {
        return ResponseEntity.status(201).body(shiftService.create(dto));
    }

    // ✅ Update shift (PATCH style, ID path variable)
    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<ShiftResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody ShiftUpdateDTO dto
    ) {
        return ResponseEntity.ok(shiftService.update(id, dto));
    }

    // ✅ Delete shift (soft delete)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<ShiftResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.delete(id));
    }

    // ✅ Get shift by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Response<ShiftResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.findById(id));
    }

    // ✅ Get all active shifts
    @GetMapping("/all")
    public ResponseEntity<Response<List<ShiftResponseDTO>>> getAll() {
        return ResponseEntity.ok(shiftService.findAll());
    }

    // Shiftni faollashtirish / deaktivatsiya qilish
    // PATCH /api/v1/shifts/{id}/activate
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Response<ShiftResponseDTO>> activate(@PathVariable Long id,
                                                               @Valid @RequestBody ActivateShiftDTO dto) {
        return ResponseEntity.ok(shiftService.activate(id, dto));
    }// Shift qidirish / filterlash

    // GET /api/v1/shifts?name=MORNING&active=true&page=0&size=20&sort=startTime,asc
    @GetMapping
    public ResponseEntity<Response<List<ShiftResponseDTO>>> getWithFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "active", required = false) Boolean active,
            @RequestParam(value = "startTime", required = false) LocalTime startTime,
            Pageable pageable) {

        return ResponseEntity.ok(shiftService.filter(name, active, startTime, pageable));
    }

    // Shift history / audit
    // GET /api/v1/shifts/{id}/history
    @GetMapping("/{id}/history")
    public ResponseEntity<Response<ShiftHistoryDTO>> getShiftHistory(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.shiftHistory(id));
    }

    // Shift availability check
    // GET /api/v1/shifts/available?date=2026-02-22
    @GetMapping("/available")
    public ResponseEntity<Response<List<ShiftResponseDTO>>> availableShifts() {
        return ResponseEntity.ok(shiftService.availableShifts());
    }

    // Bulk create / bulk update
    // POST /api/v1/shifts/bulk
    @PostMapping("/bulk")
    public ResponseEntity<Response<List<ShiftResponseDTO>>> bulk(
            @Valid @RequestBody List<ShiftCreateDTO> bulkShifts) {

        return ResponseEntity.ok(shiftService.bulkCreate(bulkShifts));
    }
    // Duplicate check
    // GET /api/v1/shifts/check-duplicate?name=MORNING&startTime=09:00&endTime=18:00
    @GetMapping("/check-duplicate")
    public ResponseEntity<Response<ShiftResponseDTO>> duplicateCheck(
            @RequestParam("name") String name,
            @RequestParam
            @Schema(type = "string", example = "09:00:00", pattern = "HH:mm:ss")
            LocalTime startTime,
            @RequestParam
            @Schema(type = "string", example = "18:00:00", pattern = "HH:mm:ss")
            LocalTime endTime) {

        return ResponseEntity.ok(shiftService.duplicateCheck(name, startTime, endTime));
    }
}
