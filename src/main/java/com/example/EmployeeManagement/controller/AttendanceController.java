package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.service.AttendanceService;
import com.example.EmployeeManagement.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_PATH_V1 + "/attendance")
public class AttendanceController {

//    private final AttendanceService service;
//
//    @PatchMapping("/approve/{id}")
//    public ResponseEntity<Response<AttendanceResponseDTO>> approve(
//            @PathVariable Long id,
//            @RequestParam Long approverId) {
//
//        AttendanceResponseDTO approved = service.approveAttendance(id, approverId);
//        return ResponseEntity.ok(Response.ok(approved));
//    }
}
