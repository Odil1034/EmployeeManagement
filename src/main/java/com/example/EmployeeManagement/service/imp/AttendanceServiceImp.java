package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.config.SessionUser;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.AttendanceCheckOutDTO;
import com.example.EmployeeManagement.dto.request.AttendanceCreateDTO;
import com.example.EmployeeManagement.dto.request.AttendanceFilterDTO;
import com.example.EmployeeManagement.dto.request.AttendanceUpdateDTO;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.dto.response.DashboardAttendanceDTO;
import com.example.EmployeeManagement.dto.response.MonthlyAttendanceSummaryDTO;
import com.example.EmployeeManagement.entity.Attendance;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import com.example.EmployeeManagement.enums.AttendanceStatus;
import com.example.EmployeeManagement.enums.CheckInStatus;
import com.example.EmployeeManagement.enums.CheckOutStatus;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.AttendanceMapper;
import com.example.EmployeeManagement.repository.AttendanceRepository;
import com.example.EmployeeManagement.service.AttendanceService;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.ShiftAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImp implements AttendanceService {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;
    private final EmployeeService employeeService;
    private final ShiftAssignmentService shiftAssignmentService;
    private final SessionUser sessionUser;

    @Override
    public Response<AttendanceResponseDTO> create(AttendanceCreateDTO dto) {
        // 1️⃣ Employee va ShiftAssignment olish
        Long userID = sessionUser.getID();
        Employee employee = employeeService.findByUserId(userID);

        ShiftAssignment shiftAssignment = shiftAssignmentService.find(dto.shiftAssignmentId());

        // 2️⃣ Duplicate check
        if (repository.existsByEmployeeAndShiftAssignmentAndDate(employee, shiftAssignment, LocalDate.now())) {
            throw new RuntimeException("Attendance already exists for this employee, shift, and date");
        }

        // 3️⃣ CheckIn va CheckInStatus belgilash
        LocalTime checkIn = LocalTime.now().withNano(0);
        CheckInStatus checkInStatus = getCheckInStatus(shiftAssignment, checkIn);

        // 4️⃣ Attendance entity yaratish
        Attendance attendance = Attendance.builder()
                .employee(employee)
                .shiftAssignment(shiftAssignment)
                .checkIn(checkIn)
                .checkInStatus(checkInStatus)
                .status(AttendanceStatus.PRESENT)
                .date(LocalDate.now())
                .isApproved(false) // default approval
                .build();

        // 5️⃣ DB ga saqlash
        Attendance saved = repository.save(attendance);

        // 6️⃣ ResponseDTO ga map qilish va qaytarish
        AttendanceResponseDTO responseDTO = mapper.toDTO(saved);
        return Response.ok(HttpStatus.CREATED, responseDTO);
    }

    private static CheckInStatus getCheckInStatus(ShiftAssignment shiftAssignment, LocalTime checkIn) {
        LocalTime shiftStart = shiftAssignment.getShift().getStartTime();
        int gracePeriodMinutes = shiftAssignment.getShift().getGracePeriodMinutes() != null
                ? shiftAssignment.getShift().getGracePeriodMinutes()
                : 0;

        CheckInStatus checkInStatus;
        if (checkIn == null) {
            checkInStatus = CheckInStatus.ABSENT;
        } else if (checkIn.isAfter(shiftStart.plusMinutes(gracePeriodMinutes))) {
            checkInStatus = CheckInStatus.LATE;
        } else {
            checkInStatus = CheckInStatus.ON_TIME;
        }
        return checkInStatus;
    }

    @Override
    public Response<AttendanceResponseDTO> update(Long id, AttendanceUpdateDTO dto) {
        Attendance attendance = find(id);
        // CheckIn update bo‘lsa
        if (dto.checkIn() != null) {
            attendance.setCheckIn(dto.checkIn());
            CheckInStatus newStatus = getCheckInStatus(
                    attendance.getShiftAssignment(),
                    dto.checkIn()
            );
            attendance.setCheckInStatus(newStatus);
        }

        // Status update (agar kelsa)
        if (dto.status() != null) {
            attendance.setStatus(dto.status());
        }

        Attendance updated = repository.save(attendance);

        return Response.ok(HttpStatus.OK, mapper.toDTO(updated));
    }

    private Attendance find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: {0}", id));
    }

    @Override
    public Response<AttendanceResponseDTO> delete(Long id) {
        Attendance attendance = find(id);

        attendance.setDeleted(true); // agar shunaqa field bo‘lsa
        Attendance deleted = repository.save(attendance);

        return Response.ok(HttpStatus.OK, mapper.toDTO(deleted));
    }

    @Override
    public Response<AttendanceResponseDTO> findById(Long id) {
        Attendance attendance = find(id);
        return Response.ok(mapper.toDTO(attendance));
    }

    @Override
    public Response<List<AttendanceResponseDTO>> findAll() {
        List<AttendanceResponseDTO> list = repository.findAllByDeletedFalse()
                .stream()
                .map(mapper::toDTO)
                .toList();

        return Response.ok(HttpStatus.OK, list);
    }

    @Override
    public Response<AttendanceResponseDTO> approveAttendance(Long id) {
        Long userID = sessionUser.getID();
        Employee approver = employeeService.findByUserId(userID);
        Attendance attendance = find(id);
        if (attendance.getIsApproved()) {
            throw new RuntimeException("Attendance already approved");
        }

        attendance.setIsApproved(true);
        attendance.setApprovedBy(approver.getId());
        attendance.setApprovedAt(LocalDateTime.now());

        Attendance saved = repository.save(attendance);

        return Response.ok(mapper.toDTO(saved));
    }

    @Override
    public Response<AttendanceResponseDTO> checkOut(AttendanceCheckOutDTO dto) {
        Long userId = sessionUser.getID();
        Employee employee = employeeService.findByUserId(userId);
        ShiftAssignment shiftAssignment = shiftAssignmentService.find(dto.shiftAssignmentId());

        LocalDate today = LocalDate.now();

        Attendance attendance = repository
                .findByEmployeeAndShiftAssignmentAndDate(employee, shiftAssignment, today)
                .orElseThrow(() -> new RuntimeException("Attendance not found for today"));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Employee already checked out");
        }
        if (attendance.getCheckIn() == null) {
            throw new RuntimeException("Cannot check out without check in");
        }

        LocalTime now = LocalTime.now().withNano(0);
        LocalTime shiftEnd = shiftAssignment.getShift().getEndTime();

        attendance.setCheckOut(now);

        // 🔹 CheckOutStatus hisoblash
        if (now.isBefore(shiftEnd)) {
            attendance.setCheckOutStatus(CheckOutStatus.LEFT_EARLY);
        } else {
            attendance.setCheckOutStatus(CheckOutStatus.ON_TIME);
        }

        repository.save(attendance);
        return Response.ok(HttpStatus.OK, mapper.toDTO(attendance));
    }

    @Override
    public Response<List<AttendanceResponseDTO>> getDailyAttendance(LocalDate date) {
        Long userId = sessionUser.getID();
        Employee employee = employeeService.findByUserId(userId);

        List<AttendanceResponseDTO> list = repository
                .findAllByEmployeeAndDateAndDeletedFalse(employee, date)
                .stream()
                .map(mapper::toDTO)
                .toList();

        return Response.ok(HttpStatus.OK, list);
    }

    @Override
    public Response<List<AttendanceResponseDTO>> getDepartmentDailyAttendance(Long departmentId, LocalDate date) {

        List<AttendanceResponseDTO> list = repository
                .findAllByDepartmentAndDate(departmentId, date)
                .stream()
                .map(mapper::toDTO)
                .toList();

        return Response.ok(HttpStatus.OK, list);
    }

    @Override
    public Response<MonthlyAttendanceSummaryDTO> getMonthlySummary(Long employeeId, int year, int month) {
        employeeService.find(employeeId);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        MonthlyAttendanceSummaryDTO summary = repository.getMonthlySummary(employeeId, start, end);

       /* @Query("""
                SELECT
                    COUNT(a),
                    SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
                    SUM(CASE WHEN a.checkInStatus = 'LATE' THEN 1 ELSE 0 END),
                    SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END)
                FROM Attendance a
                WHERE a.employee.id = :employeeId
                AND a.date BETWEEN :start AND :end
                AND a.deleted = false
            """)
        Object[] getMonthlySummary(@Param("employeeId") Long employeeId,
                @Param("start") LocalDate start,
                @Param("end") LocalDate end);*/

        return Response.ok(HttpStatus.OK, summary);
    }

    @Override
    public Response<DashboardAttendanceDTO> getTodayDashboardAttendanceSummary() {
        LocalDate today = LocalDate.now();

        long present = repository.countByDateAndStatus(today, AttendanceStatus.PRESENT);
        long late = repository.countByDateAndCheckInStatus(today, CheckInStatus.LATE);
        long absent = repository.countByDateAndStatus(today, AttendanceStatus.ABSENT);

        DashboardAttendanceDTO dto = new DashboardAttendanceDTO(present, late, absent);

        return Response.ok(HttpStatus.OK, dto);
    }

    @Override
    public Response<List<AttendanceResponseDTO>> filterAttendance(AttendanceFilterDTO dto) {
        List<Attendance> list = repository.filterAttendance(
                dto.employeeId(),
                dto.departmentId(),
                dto.status(),
                dto.checkInStatus(),
                dto.from(),
                dto.to()
        );

        List<AttendanceResponseDTO> result = list.stream()
                .map(mapper::toDTO)
                .toList();

        return Response.ok(HttpStatus.OK, result);
    }

    public long calculateOvertimeMinutes(Attendance attendance) {
        if (attendance.getCheckOut() == null || attendance.getShiftAssignment() == null)
            return 0;

        LocalTime shiftEnd = attendance.getShiftAssignment().getShift().getEndTime();
        LocalTime checkOut = attendance.getCheckOut();

        if (checkOut.isAfter(shiftEnd)) {
            return java.time.Duration.between(shiftEnd, checkOut).toMinutes();
        } else {
            return 0;
        }
    }
}
