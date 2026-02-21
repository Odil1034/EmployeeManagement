package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.ShiftCreateDTO;
import com.example.EmployeeManagement.dto.request.ShiftUpdateDTO;
import com.example.EmployeeManagement.dto.response.ShiftHistoryDTO;
import com.example.EmployeeManagement.dto.response.ShiftResponseDTO;
import com.example.EmployeeManagement.entity.Shift;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    Shift fromCreate(ShiftCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(ShiftUpdateDTO dto, @MappingTarget Shift entity);

    ShiftResponseDTO toDTO(Shift entity);

    Shift toEntity(ShiftResponseDTO dto);

    ShiftHistoryDTO toHistoryDTO(Shift shift);
}
