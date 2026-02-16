package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, Repository {

    @Query(value = """
                SELECT * FROM all_users u
                WHERE u.username = :username
                LIMIT 1
            """, nativeQuery = true)
    Optional<User> findByUsernameNative(@Param("username") String username);


    @Query(value = """
                SELECT
                    r.id AS role_id,
                    r.name AS role_name,
                    p.id AS permission_id,
                    p.name AS permission_name,
                    p.description AS permission_description
                FROM all_users u
                JOIN user_roles ur ON ur.user_id = u.id
                JOIN roles r ON r.id = ur.role_id
                LEFT JOIN role_permissions rp ON rp.role_id = r.id
                LEFT JOIN permissions p ON p.id = rp.permission_id
                WHERE u.username = :username
            """, nativeQuery = true)
    List<Object[]> findRolesWithPermissions(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.isDeleted = FALSE")
    Optional<User> findByIdCustom(@NotNull Long id);

    @Query("SELECT u FROM User u WHERE u.isDeleted = FALSE")
    List<User> findAllCustom();

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = TRUE WHERE u.id = ?1")
    void softDelete(@NotNull Long id);

    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.isDeleted = FALSE")
    Optional<User> findByUsername(@NotNull String username);
}
