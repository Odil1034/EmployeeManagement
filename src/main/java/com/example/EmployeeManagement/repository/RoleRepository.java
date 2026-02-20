package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long>, Repository {

    @Query(
            value = """
            SELECT r.*
            FROM roles r
            JOIN user_roles ur ON r.id = ur.role_id
            JOIN all_users u ON ur.user_id = u.id
            WHERE u.username = :username
        """,
            nativeQuery = true
    )
    Set<Role> findRolesByUsername(@Param("username") String username);

    @Query("SELECT u FROM Role u WHERE u.name = ?1 AND u.deleted = FALSE")
    Optional<Role> findByName(@NotNull String name);

    @Query("SELECT u FROM Role u WHERE u.id = ?1 AND u.deleted = FALSE")
    Optional<Role> findByIdCustom(@NotNull Long id);

    @Query("SELECT u FROM Role u WHERE u.deleted = FALSE")
    Set<Role> findAllCustom();

    @Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.name = :name")
    Optional<Role> findRoleWithPermissionsByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Role u SET u.deleted = TRUE WHERE u.id = ?1")
    void softDelete(@NotNull Long id);

    Optional<Role> findRoleWithPermissionsById(Long id);

    @Query("""
            SELECT DISTINCT r FROM Role r
            LEFT JOIN FETCH r.permissions
            """)
    Set<Role> findAllWithPermissions();

    boolean existsByName(@NotNull String name);

    Optional<Role> findByIdAndDeletedFalse(Long id);
}
