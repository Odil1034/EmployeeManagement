package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Permission;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long>, Repository {

    @Query("SELECT u FROM Permission u WHERE u.name = ?1 AND u.isDeleted = FALSE")
    Optional<Permission> findByName(@NotNull String username);

    @Query("SELECT u FROM Permission u WHERE u.id = ?1 AND u.isDeleted = FALSE")
    Optional<Permission> findByIdCustom(@NotNull Long id);

    @Query("SELECT u FROM Permission u WHERE u.isDeleted = FALSE")
    List<Permission> findAllCustom();

    @Modifying
    @Query("UPDATE Permission u SET u.isDeleted = TRUE WHERE u.id = ?1")
    void softDelete(@NotNull Long id);

    @Query(
            value = """
            SELECT p.*
            FROM permissions p
            JOIN role_permissions rp ON p.id = rp.permission_id
            JOIN user_roles ur ON rp.role_id = ur.role_id
            JOIN users u ON ur.user_id = u.id
            WHERE u.username = :username
        """,
            nativeQuery = true)
    Set<Permission> findPermissionsByUsername(@Param("username") String username);

    boolean existsByAccess(@NotNull String access);
}
