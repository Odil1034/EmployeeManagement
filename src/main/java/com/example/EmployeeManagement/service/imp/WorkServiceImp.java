package com.example.EmployeeManagement.service.imp;

import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.request.WorkCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkEmpUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkResponseDTO;
import com.example.EmployeeManagement.entity.work.Work;
import com.example.EmployeeManagement.entity.work.WorkType;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.mapper.WorkMapper;
import com.example.EmployeeManagement.repository.WorkRepository;
import com.example.EmployeeManagement.service.WorkService;
import com.example.EmployeeManagement.service.WorkTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkServiceImp implements WorkService {

    private final WorkRepository repository;
    private final WorkMapper mapper;
    private final WorkTypeService workTypeService;

    @Override
    public Response<WorkResponseDTO> create(WorkCreateDTO dto) {
        WorkType workType = workTypeService.find(dto.workTypeId());
        Work work = mapper.fromCreate(dto);
        work.setPrice(workType.getDefaultPrice());

        Work save = repository.save(work);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Response<WorkResponseDTO> update(Long id, WorkEmpUpdateDTO dto) {
        Work work = find(id);
        mapper.fromUpdate(dto, work);

        Work save = repository.save(work);
        return Response.ok(mapper.toDTO(save));
    }

    @Override
    @Transactional
    public Response<WorkResponseDTO> delete(Long id) {
        Work work = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found or already deleted with id: " + id));

        work.setDeleted(true);
        Work save = repository.save(work);

        return Response.ok(mapper.toDTO(save));
    }

    @Override
    public Work find(Long id) {
        return repository.findByIdCustom(id)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found with id: {0}", id));
    }

    @Override
    public Response<List<WorkResponseDTO>> getByEmployee(Long employeeId) {
        return null;
    }

    @Override
    public Response<List<WorkResponseDTO>> getByWorkType(Long workTypeId) {
        return null;
    }

    @Override
    public Response<List<WorkResponseDTO>> getByCategory(Long workCategoryId) {
        return null;
    }

    @Override
    public Response<List<WorkResponseDTO>> search(String keyword) {
        return null;
    }

    @Override
    public Response<BigDecimal> calculateTotal(Long workId) {
        return null;
    }

    @Override
    public Response<WorkResponseDTO> findById(Long id) {
        return Response.ok(mapper.toDTO(find(id)));
    }

    @Override
    public Response<List<WorkResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream()
                .map(mapper::toDTO)
                .toList());
    }
}
