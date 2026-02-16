package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.PermissionCreateDTO;
import com.example.EmployeeManagement.dto.response.PermissionResponseDTO;
import com.example.EmployeeManagement.dto.request.PermissionUpdateDTO;
import com.example.EmployeeManagement.entity.Permission;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {

    Permission fromCreate(PermissionCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(PermissionUpdateDTO dto, @MappingTarget Permission entity);

    PermissionResponseDTO toDTO(Permission permission);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Permission toEntity(PermissionResponseDTO dto);

}
