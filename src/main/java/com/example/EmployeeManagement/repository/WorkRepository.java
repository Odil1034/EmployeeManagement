package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {

    Optional<Work> findByIdAndDeletedFalse(Long id);

    @Query("SELECT a FROM Work a WHERE a.id = :id AND a.deleted = false")
    Optional<Work> findByIdCustom(Long id);

}
