package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.WorkCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkEmpUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkResponseDTO;
import com.example.EmployeeManagement.entity.work.Work;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
public interface WorkMapper {

    // ------------------- CREATE -------------------
    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "workType.id", source = "workTypeId")
    Work fromCreate(WorkCreateDTO dto);

    // ------------------- UPDATE -------------------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(WorkEmpUpdateDTO dto, @MappingTarget Work entity);

    // ------------------- TO DTO -------------------
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName",
            expression = "java(entity.getEmployee() != null ? entity.getEmployee().getFullName() : null)")
    @Mapping(target = "workTypeId", source = "workType.id")
    @Mapping(target = "workTypeName",
            expression = "java(entity.getWorkType() != null ? entity.getWorkType().getName() : null)")
    @Mapping(target = "approvedByName",
            expression = "java(entity.getApprovedBy() != null ? entity.getApprovedBy().getFullName() : null)")
    @Mapping(target = "totalAmount", source = ".", qualifiedByName = "calculateTotal")
    WorkResponseDTO toDTO(Work entity);

}