package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM Permission p WHERE p.access = ?1 AND p.deleted = FALSE")
    Optional<Permission> findByAccess(String access);

    @Query("SELECT p FROM Permission p WHERE p.id = ?1 AND p.deleted = FALSE")
    Optional<Permission> findActiveById(Long id);

    @Query("SELECT p FROM Permission p WHERE p.deleted = FALSE")
    List<Permission> findAllActive();

    @Modifying
    @Query("UPDATE Permission p SET p.deleted = true WHERE p.id = :id")
    int softDelete(@Param("id") Long id);

    boolean existsByAccess(String access);

    boolean existsByAccessAndIdNot(String access, Long id);

    @Query(value = """
            SELECT p.*
            FROM permissions p
            JOIN role_permission rp ON p.id = rp.permission_id
            JOIN user_roles ur ON rp.role_id = ur.role_id
            JOIN all_users u ON ur.user_id = u.id
            WHERE u.username = :username
        """, nativeQuery = true)
    Set<Permission> findPermissionsByUsername(@Param("username") String username);

    Optional<Permission> findByIdAndDeletedFalse(Long id);
}
