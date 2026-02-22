package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.AttendanceCreateDTO;
import com.example.EmployeeManagement.dto.request.AttendanceUpdateDTO;
import com.example.EmployeeManagement.dto.response.AttendanceResponseDTO;
import com.example.EmployeeManagement.entity.Attendance;
import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.ShiftAssignment;
import com.example.EmployeeManagement.enums.AttendanceStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
public interface AttendanceMapper {

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "shiftAssignmentId", target = "shiftAssignment")
    Attendance fromCreate(AttendanceCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(AttendanceUpdateDTO dto, @MappingTarget Attendance entity);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "employeeFullName",
            expression = "java(attendance.getEmployee().getFirstName() + \" \" + attendance.getEmployee().getLastName())")
    @Mapping(target = "shiftId", expression = "java(attendance.getShiftAssignment().getShift().getId())")
    @Mapping(target = "shiftName", expression = "java(attendance.getShiftAssignment().getShift().getName().name())")
    @Mapping(source = "shiftAssignment", target = "shiftAssignmentId", qualifiedByName = "mapShiftAssignment")
    @Mapping(source = "employee", target = "employeeId", qualifiedByName = "mapEmployee")
    AttendanceResponseDTO toDTO(Attendance attendance);

    @Named("mapShiftAssignment")
    default Long mapShiftAssignment(ShiftAssignment shiftAssignment) {
        return shiftAssignment != null ? shiftAssignment.getId() : null;
    }

    @Named("mapEmployee")
    default Long mapEmployee(Employee employee) {
        return employee != null ? employee.getId() : null;
    }

    default String mapStatus(AttendanceStatus status){
        return status != null ? status.name() : null;
    }

    @InheritConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attendance toEntity(AttendanceResponseDTO dto);
}
