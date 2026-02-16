package com.example.EmployeeManagement.config;

import com.example.EmployeeManagement.entity.Employee;
import com.example.EmployeeManagement.entity.User;
import com.example.EmployeeManagement.enums.DefaultRole;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.repository.RoleRepository;
import com.example.EmployeeManagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class FakeDataInitializer {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        createBoss();
        createAdmins(2);
        createEmployees(10);
        createUsers(30);
        createCustomers(4);
    }

    private void createBoss() {
        if(userRepository.existsByUsername("boss")) return;

        User boss = User.builder()
                .username("boss")
                .password(passwordEncoder.encode("boss123"))
                .roles(Set.of(roleRepository.findByName(DefaultRole.BOSS.name())
                        .orElseThrow()))
                .email("boss@example.com")
                .build();

        Employee bossProfile = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .salary(10000)
                .user(boss)
                .build();

        employeeRepository.save(bossProfile); // cascade saves user
    }

    private void createAdmins(int count) {
        IntStream.rangeClosed(1, count).forEach(i -> {
            String username = "admin" + i;
            if(userRepository.existsByUsername(username)) return;

            User admin = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(roleRepository.findByName(DefaultRole.MANAGER.name())
                            .orElseThrow()))
                    .email("admin" + i + "@example.com")
                    .build();

            Employee adminProfile = Employee.builder()
                    .firstName("AdminFirst" + i)
                    .lastName("AdminLast" + i)
                    .salary(7000)
                    .user(admin)
                    .build();

            employeeRepository.save(adminProfile);
        });
    }

    private void createEmployees(int count) {
        IntStream.rangeClosed(1, count).forEach(i -> {
            String username = "employee" + i;
            if(userRepository.existsByUsername(username)) return;

            User employee = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("employee123"))
                    .roles(Set.of(roleRepository.findByName(DefaultRole.EMPLOYEE.name())
                            .orElseThrow()))
                    .email("employee" + i + "@example.com")
                    .build();

            Employee employeeProfile = Employee.builder()
                    .firstName("EmpFirst" + i)
                    .lastName("EmpLast" + i)
                    .salary(3000)
                    .user(employee)
                    .build();

            employeeRepository.save(employeeProfile);
        });
    }

    private void createUsers(int count) {
        IntStream.rangeClosed(1, count).forEach(i -> {
            String username = "user" + i;
            if(userRepository.existsByUsername(username)) return;

            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("user123"))
                    .roles(Set.of(roleRepository.findByName(DefaultRole.USER.name())
                            .orElseThrow()))
                    .email("user" + i + "@example.com")
                    .build();

            userRepository.save(user);
        });
    }

    private void createCustomers(int count) {
        IntStream.rangeClosed(1, count).forEach(i -> {
            String username = "customer" + i;
            if(userRepository.existsByUsername(username)) return;

            User customer = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("customer123"))
                    .roles(Set.of(roleRepository.findByName(DefaultRole.CUSTOMER.name())
                            .orElseThrow()))
                    .email("customer" + i + "@example.com")
                    .build();

            userRepository.save(customer);
        });
    }
}