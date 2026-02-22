package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.ShiftAssignmentCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftAssignmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftAssignmentResponseDTO;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {EmployeeMapper.class, UserMapperHelper.class})
public interface ShiftAssignmentMapper {

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "shiftId", target = "shift")
    ShiftAssignment fromCreate(ShiftAssignmentCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "shiftId", target = "shift")
    void fromUpdate(ShiftAssignmentUpdateDTO dto, @MappingTarget ShiftAssignment entity);

    ShiftAssignmentResponseDTO toDTO(ShiftAssignment entity);

}
