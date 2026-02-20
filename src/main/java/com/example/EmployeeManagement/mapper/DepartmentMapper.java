package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.DepartmentCreateDTO;
import com.example.EmployeeManagement.dto.request.DepartmentUpdateDTO;
import com.example.EmployeeManagement.dto.response.DepartmentResponseDTO;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.enums.DepartmentStatus;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapperHelper.class})
public interface DepartmentMapper {

    Department fromCreate(DepartmentCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(DepartmentUpdateDTO dto, @MappingTarget Department entity);

    @Mapping(target = "status", source = "status")
    DepartmentResponseDTO toDTO(Department permission);

    default String mapStatus(DepartmentStatus status){
        return status!= null ? status.name() : null;
    }

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Department toEntity(DepartmentResponseDTO dto);



}
