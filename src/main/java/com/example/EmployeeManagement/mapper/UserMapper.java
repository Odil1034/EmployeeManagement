package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.config.CustomUserDetails;
import com.example.EmployeeManagement.dto.request.UserCreateDTO;
import com.example.EmployeeManagement.dto.request.UserUpdateDTO;
import com.example.EmployeeManagement.dto.response.UserResponseDTO;
import com.example.EmployeeManagement.entity.User;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        uses = {RoleMapper.class, PermissionMapper.class}
)
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User fromCreate(UserCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(UserUpdateDTO dto, @MappingTarget User entity);

    UserResponseDTO toDTO(User user);

    User toEntity(UserResponseDTO dto);

    CustomUserDetails toCustomUserDetails(User user);

}
