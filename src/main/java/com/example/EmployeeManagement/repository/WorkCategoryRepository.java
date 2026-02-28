package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.work.WorkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkCategoryRepository extends JpaRepository<WorkCategory, Long> {

    Optional<WorkCategory> findByIdAndDeletedFalse(Long id);

    @Query("SELECT a FROM WorkCategory a WHERE a.id = :id AND a.deleted = false")
    Optional<WorkCategory> findByIdCustom(Long id);

    boolean existsByName(String name);
}
