package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.ActivateShiftDTO;
import com.example.EmployeeManagement.dto.request.ShiftCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftHistoryDTO;
import com.example.EmployeeManagement.dto.response.ShiftResponseDTO;
import com.example.EmployeeManagement.entity.Shift;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.ShiftMapper;
import com.example.EmployeeManagement.repository.ShiftRepository;
import com.example.EmployeeManagement.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

@Service
@RequiredArgsConstructor
public class ShiftServiceImp implements ShiftService {

    private final ShiftRepository repository;
    private final ShiftMapper mapper;

    @Override
    public Response<ShiftResponseDTO> create(ShiftCreateDTO dto) {
        // 1️⃣ Start va end vaqt tekshirish
        if (dto.startTime().equals(dto.endTime())) {
            throw new IllegalArgumentException("Start time and end time cannot be the same");
        }
        // 2️⃣ Duplicate tekshirish (DB constraint + service level)
        boolean exists = repository.existsByNameAndStartTimeAndEndTime(
                dto.name(),
                dto.startTime(),
                dto.endTime()
        );
        if (exists) {
            throw new IllegalArgumentException("Shift with same name and time already exists");
        }

        Shift shift = mapper.fromCreate(dto);
        repository.save(shift);

        return Response.ok(mapper.toDTO(shift));
    }

    @Override
    public Response<ShiftResponseDTO> update(Long id, ShiftUpdateDTO dto) {
        Shift shift = find(id);
        mapper.fromUpdate(dto, shift);

        // Duplicate tekshirish (name + start + end)
        boolean exists = repository.existsByNameAndStartTimeAndEndTimeAndIdNot(
                shift.getName(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.getId()
        );
        if (exists) {
            throw new IllegalArgumentException("Shift with same name and time already exists");
        }
        Shift save = repository.save(shift);
        return Response.ok(mapper.toDTO(save));
    }

    public Shift find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found with id: {0}", id));
    }

    @Override
    public Response<ShiftResponseDTO> delete(Long id) {
        Shift shift = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shift not found or already deleted with id: " + id));
        shift.setDeleted(true);
        shift.setActive(false);
        Shift save = repository.save(shift);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<ShiftResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    @Override
    public Response<List<ShiftResponseDTO>> findAll() {
        return Response.ok(repository.findAllCustom().stream()
                .map(mapper::toDTO)
                .toList());
    }

    @Override
    public Response<ShiftResponseDTO> activate(Long id, ActivateShiftDTO dto) {
        Shift shift = find(id);
        shift.setActive(dto.active());
        Shift save = repository.save(shift);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<List<ShiftResponseDTO>> filter(String name, Boolean active, LocalTime startTime, Pageable pageable) {
        Page<Shift> shifts = repository.findAll((Specification<Shift>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
            }
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }
            if (startTime != null) {
                predicates.add(cb.equal(root.get("startTime"), startTime));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        List<ShiftResponseDTO> responseList = shifts.stream()
                .map(mapper::toDTO)
                .toList();

        return Response.ok(200, responseList, "Filtered shifts fetched successfully");
    }

    @Override
    public Response<ShiftHistoryDTO> shiftHistory(Long id) {
        Shift shift = find(id);
        ShiftHistoryDTO historyDTO = mapper.toHistoryDTO(shift);
        return Response.ok(200, historyDTO, "Shift history fetched successfully");
    }

    @Override
    public Response<List<ShiftResponseDTO>> availableShifts() {
        List<Shift> shifts = repository.findByActiveTrue(); // custom query kerak
        List<ShiftResponseDTO> responseList = shifts.stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(200, responseList, "Available shifts fetched successfully");
    }

    @Override
    public Response<List<ShiftResponseDTO>> bulkCreate(List<ShiftCreateDTO> bulkShifts) {
        List<Shift> shifts = bulkShifts.stream()
                .map(mapper::fromCreate)
                .toList();
        List<Shift> savedShifts = repository.saveAll(shifts);

        List<ShiftResponseDTO> responseList = savedShifts.stream()
                .map(mapper::toDTO)
                .toList();
        return Response.ok(200, responseList, "Bulk shifts saved successfully");
    }

    @Override
    public Response<ShiftResponseDTO> duplicateCheck(String name, LocalTime startTime, LocalTime endTime) {
        Optional<Shift> duplicate = repository.findByNameAndStartTimeAndEndTime(name, startTime, endTime);
        if (duplicate.isPresent()) {
            Response.ok(200, mapper.toDTO(duplicate.get()), "Duplicate shift exists");
        }
        return Response.ok(200, mapper.toDTO(null), "No duplicate found");
    }
}
