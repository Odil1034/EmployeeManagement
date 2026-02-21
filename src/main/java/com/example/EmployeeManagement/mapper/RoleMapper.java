package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.RoleCreateDTO;
import com.example.EmployeeManagement.dto.response.RoleResponseDTO;
import com.example.EmployeeManagement.dto.response.RoleUpdateDTO;
import com.example.EmployeeManagement.entity.Role;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapperHelper.class})
public interface RoleMapper {

    default Role fromCreate(RoleCreateDTO dto) {
        return new Role(dto.name(), dto.description(), null, null);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(RoleUpdateDTO dto, @MappingTarget Role entity);

    RoleResponseDTO toDTO(Role entity);

    Role toEntity(RoleResponseDTO dto);

}