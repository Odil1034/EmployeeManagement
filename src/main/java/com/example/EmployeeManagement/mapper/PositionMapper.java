package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.PositionCreateDTO;
import com.example.EmployeeManagement.dto.request.PositionUpdateDTO;
import com.example.EmployeeManagement.dto.response.PositionResponseDTO;
import com.example.EmployeeManagement.entity.Position;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    Position fromCreate(PositionCreateDTO dto);

    // Update DTO → Entity (nullable fieldlar update qilinmaydi)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(PositionUpdateDTO dto, @MappingTarget Position entity);

    // Entity → ResponseDTO
    PositionResponseDTO toDTO(Position entity);

    // ResponseDTO → Entity (kam ishlatiladi, lekin MapStruct uchun)
    Position toEntity(PositionResponseDTO dto);
}
