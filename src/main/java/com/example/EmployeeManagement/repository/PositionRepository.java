package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByIdAndDeletedFalse(Long id);

    @Query("SELECT a FROM Position a WHERE a.id = :id AND a.deleted = false")
    Optional<Position> findByIdCustom(Long id);

    boolean existsByName(String name);

    @Query("SELECT p FROM Position p WHERE p.deleted = false")
    List<Position> findAllCustom();
}
