package com.example.EmployeeManagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.EmployeeManagement.enums.DefaultPermission.*;

@Getter
@AllArgsConstructor
public enum DefaultRole {

    EMPLOYEE("An employee who can manage their own profile, create and track their assigned work, and view personal salary reports. Has limited access to system data related to their responsibilities.",
            Set.of(VIEW_OWN_PROFILE,
                    UPDATE_OWN_PROFILE,
                    VIEW_PRODUCTS,
                    VIEW_ALL_EMPLOYEES,
                    VIEW_OWN_WORKS,
                    VIEW_WORK_TYPES,
                    CREATE_OWN_WORK,
                    VIEW_OWN_SALARY_REPORT
            )),
    MANAGER("A manager responsible for overseeing employees, managing work types and prices, assigning roles, and approving or rejecting work. Has extended operational control within the organization.",
            Set.of(VIEW_OWN_PROFILE,
                    UPDATE_OWN_PROFILE,
                    VIEW_PRODUCTS,
                    VIEW_ALL_EMPLOYEES,
                    VIEW_OWN_WORKS,
                    VIEW_WORK_TYPES,
                    MANAGE_USERS,
                    MANAGE_WORK_TYPES,
                    MANAGE_WORK_PRICES,
                    ASSIGN_ROLES,
                    UPDATE_WORK,
                    CREATE_WORK,
                    APPROVE_WORK,
                    REJECT_WORK)),
    BOSS("The business owner or top-level executive with full control over employees, products, financial reports, work management, and strategic operations. Has the highest level of business authority.",
            Set.of(VIEW_OWN_PROFILE,
                    UPDATE_OWN_PROFILE,
                    ADD_EMPLOYEE,
                    UPDATE_EMPLOYEE,
                    DELETE_EMPLOYEE,
                    VIEW_PRODUCTS,
                    VIEW_ALL_EMPLOYEES,
                    VIEW_OWN_WORKS,
                    VIEW_WORK_TYPES,
                    MANAGE_USERS,
                    MANAGE_WORK_TYPES,
                    MANAGE_WORK_PRICES,
                    MANAGE_ROLES,
                    APPROVE_PAYMENTS,
                    VIEW_WORK_PRICES,
                    VIEW_ALL_WORKS,
                    ASSIGN_ROLES,
                    VIEW_FINANCIAL_REPORTS,
                    VIEW_MONTHLY_SALARY_REPORT,
                    VIEW_DAILY_ATTENDANCE_REPORT,
                    VIEW_ALL_ORDERS,
                    CREATE_PRODUCT,
                    UPDATE_PRODUCT,
                    DELETE_PRODUCT,
                    UPDATE_WORK,
                    CREATE_WORK,
                    APPROVE_WORK,
                    REJECT_WORK,
                    DELETE_WORK)),
    CUSTOMER("The business owner or top-level executive with full control over employees, products, financial reports, work management, and strategic operations. Has the highest level of business authority.",
            Set.of(VIEW_OWN_PROFILE,
                    UPDATE_OWN_PROFILE,
                    VIEW_PRODUCTS,
                    CREATE_ORDER,
                    UPDATE_OWN_ORDER,
                    VIEW_OWN_ORDERS)),
    ACCOUNTANT("A financial officer responsible for viewing salary reports, financial reports, and employee-related financial data. Has access to financial insights but limited operational control.",
            Set.of(VIEW_OWN_PROFILE,
                    UPDATE_OWN_PROFILE,
                    VIEW_PRODUCTS,
                    VIEW_ALL_EMPLOYEES,
                    VIEW_MONTHLY_SALARY_REPORT,
                    VIEW_FINANCIAL_REPORTS)),
    USER("A basic authenticated user with access to their own profile and product viewing functionality. Has minimal system permissions.",
            Set.of(VIEW_OWN_PROFILE,
                    UPDATE_OWN_PROFILE,
                    VIEW_PRODUCTS)),
    SUPER_ADMIN("A system-level administrator responsible for system configuration and global platform management. Has ultimate technical authority over the system.",
            Set.of(FULL_ACCESS));

    private final String description;
    private final Set<DefaultPermission> permissions;


    public List<SimpleGrantedAuthority> getAuthorities() {
        if (getPermissions() != null) {
            List<SimpleGrantedAuthority> authorities = getPermissions()
                    .stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.name()))
                    .toList();
            return authorities;
        }
        return new ArrayList<>();
    }
}
