package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.work.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

    Optional<WorkType> findByIdAndDeletedFalse(Long id);

    @Query("SELECT a FROM WorkType a WHERE a.id = :id AND a.deleted = false")
    Optional<WorkType> findByIdCustom(Long id);

    boolean existsByName(String name);
}
