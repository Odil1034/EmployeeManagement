package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.AttendanceCheckOutDTO;
import com.example.EmployeeManagement.dto.request.AttendanceCreateDTO;
import com.example.EmployeeManagement.dto.request.AttendanceUpdateDTO;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.dto.response.DashboardAttendanceDTO;
import com.example.EmployeeManagement.dto.response.MonthlyAttendanceSummaryDTO;
import com.example.EmployeeManagement.service.AttendanceService;
import com.example.EmployeeManagement.utils.Constants;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_PATH_V1 + "/attendance")
public class AttendanceController {

    private final AttendanceService service;

    @PostMapping("/check-in")
    public ResponseEntity<Response<AttendanceResponseDTO>> checkIn(@Valid @RequestBody AttendanceCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PostMapping("/check-out")
    public ResponseEntity<Response<AttendanceResponseDTO>> checkOut(@Valid @RequestBody AttendanceCheckOutDTO dto) {
        return ResponseEntity.ok(service.checkOut(dto));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<Response<AttendanceResponseDTO>> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveAttendance(id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<AttendanceResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<AttendanceResponseDTO>>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<AttendanceResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response<AttendanceResponseDTO>> update(@PathVariable Long id,
                                                                  @Valid @RequestBody AttendanceUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // GET /attendance/daily?date=2026-02-23        Employee daily attendance
    @GetMapping("/daily")
    public ResponseEntity<Response<List<AttendanceResponseDTO>>> getDailyAttendance(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(
                    description = "Date in yyyy-MM-dd format",
                    example = "2026-02-23"
            )
            LocalDate date
    ) {
        return ResponseEntity.ok(service.getDailyAttendance(date));
    }

    // Manager bugungi kim kelgan-kelmaganini ko‘radi.
    // GET /attendance/daily/department/{departmentId}?date=2026-02-23
    @GetMapping("/daily/department/{departmentId}")
    public ResponseEntity<Response<List<AttendanceResponseDTO>>> getDepartmentDailyAttendance(
            @PathVariable Long departmentId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(
                    description = "Date in yyyy-MM-dd format",
                    example = "2026-02-23"
            )
            LocalDate date){
        return ResponseEntity.ok(service.getDepartmentDailyAttendance(departmentId, date));
    }

    // GET /attendance/monthly?employeeId=1&year=2026&month=2       monthly attendance
    @GetMapping("/monthly")
    public ResponseEntity<Response<MonthlyAttendanceSummaryDTO>> getMonthlyAttendance(@RequestParam Long employeeId,
                                                                                      int year,
                                                                                      int month){
        return ResponseEntity.ok(service.getMonthlySummary(employeeId, year, month));
    }

    // GET /attendance/dashboard-summary
    @GetMapping("/dashboard-summary")
    public ResponseEntity<Response<DashboardAttendanceDTO>> getDashboardAttendance(){
        return ResponseEntity.ok(service.getTodayDashboardAttendanceSummary());
    }
}
