package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.RoleCreateDTO;
import com.example.EmployeeManagement.dto.response.RoleResponseDTO;
import com.example.EmployeeManagement.dto.response.RoleUpdateDTO;
import com.example.EmployeeManagement.entity.Role;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {PermissionMapper.class})

public interface RoleMapper {

    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    Role fromCreate(RoleCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(RoleUpdateDTO dto, @MappingTarget Role entity);

    RoleResponseDTO toDTO(Role permission);

    Role toEntity(RoleResponseDTO dto);

}