package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.WorkCategoryCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkCategoryUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkCategoryResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkCategory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WorkCategoryMapper {

    WorkCategory fromCreate(WorkCategoryCreateDTO dto);

    // Update DTO → Entity (nullable fieldlar update qilinmaydi)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUpdate(WorkCategoryUpdateDTO dto, @MappingTarget WorkCategory entity);

    // Entity → ResponseDTO
    WorkCategoryResponseDTO toDTO(WorkCategory entity);

    // ResponseDTO → Entity (kam ishlatiladi, lekin MapStruct uchun)
    WorkCategory toEntity(WorkCategoryResponseDTO dto);
}