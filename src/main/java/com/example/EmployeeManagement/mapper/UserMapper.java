package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.UserCreateDTO;
import com.example.EmployeeManagement.dto.request.UserUpdateDTO;
import com.example.EmployeeManagement.dto.response.UserResponseDTO;
import com.example.EmployeeManagement.entity.User;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, PermissionMapper.class})
public interface UserMapper {

    User fromCreate(UserCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(UserUpdateDTO dto, @MappingTarget User entity);

    UserResponseDTO toDTO(User user);

    User toEntity(UserResponseDTO dto);



    //
//    @Mapping(target = "roles", source = "roles")
//    @Mapping(target = "permissions", source = "permissionIds")
    // MapStruct uchun default methodlar
    /*Set<Role> toRoleSet(Set<Long> roleIds);
    Set<Role> toRoles(Set<String> roleStrs);
    Set<Permission> toPermissionSet(Set<Long> permissionIds);*/

}
