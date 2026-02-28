package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.dto.request.WorkTypeCreateDTO;
import com.example.EmployeeManagement.dto.request.WorkTypeUpdateDTO;
import com.example.EmployeeManagement.dto.response.WorkTypeResponseDTO;
import com.example.EmployeeManagement.entity.work.WorkType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WorkTypeMapper {

    // Create DTO → Entity
    @Mapping(target = "category.id", source = "workCategoryId") // DTO da workCategoryId, entity.category.id ga map qilinadi
    @Mapping(target = "defaultPrice", expression = "java(dto.defaultPrice() != null ? new java.math.BigDecimal(dto.defaultPrice()) : null)")
    WorkType fromCreate(WorkTypeCreateDTO dto);

    // Update DTO → Entity (nullable fieldlar update qilinmaydi)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "workCategoryId")
    @Mapping(target = "defaultPrice", expression = "java(dto.defaultPrice() != null ? new java.math.BigDecimal(dto.defaultPrice()) : null)")
    void fromUpdate(WorkTypeUpdateDTO dto, @MappingTarget WorkType entity);

    // Entity → ResponseDTO
    @Mapping(target = "workCategoryId", source = "category.id")
    @Mapping(target = "workCategoryName", source = "category.name")
    @Mapping(target = "defaultPrice", source = "defaultPrice")
    WorkTypeResponseDTO toDTO(WorkType entity);

    // ResponseDTO → Entity (kam ishlatiladi, lekin MapStruct uchun)
    @Mapping(target = "category.id", source = "workCategoryId")
    @Mapping(target = "defaultPrice", source = "defaultPrice")
    WorkType toEntity(WorkTypeResponseDTO dto);
}