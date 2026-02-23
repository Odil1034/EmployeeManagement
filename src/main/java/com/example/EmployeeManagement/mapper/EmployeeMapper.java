package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.EmployeeCreateDTO;
import com.example.EmployeeManagement.dto.request.EmployeeUpdateDTO;
import com.example.EmployeeManagement.dto.response.EmployeeResponseDTO;
import com.example.EmployeeManagement.dto.response.SimpleEmployeeDTO;
import com.example.EmployeeManagement.entity.Department;
import com.example.EmployeeManagement.entity.Employee;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {UserMapperHelper.class})
public interface EmployeeMapper {

    Employee fromCreate(EmployeeCreateDTO dto);

    default Department map(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        Department dep = new Department();
        dep.setId(departmentId);
        return dep;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(EmployeeUpdateDTO dto, @MappingTarget Employee entity);

    @Mapping(target = "departmentId", expression = "java(employee.getDepartment().getId())")
    @Mapping(target = "departmentName", expression = "java(employee.getDepartment().getName())")
    EmployeeResponseDTO toDTO(Employee employee);

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee toEntity(EmployeeResponseDTO dto);

    @Mapping(target = "fullName",
            expression = "java(employee.getFirstName() + \" \" + employee.getLastName())")
    SimpleEmployeeDTO toSimpleDTO(Employee employee);

}
