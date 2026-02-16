package com.example.EmployeeManagement.enums;

import lombok.Getter;

@Getter
public enum DefaultPermission {
    // Report — bu tizimdagi ma’lumotlarni hisobot ko‘rinishida chiqarish.

    // ===== COMMON / USER =====
    VIEW_OWN_PROFILE("View own profile"),
    UPDATE_OWN_PROFILE("Update own profile"),

    VIEW_OWN_WORKS("View own completed works"),
    CREATE_OWN_WORK("Add own completed work"),

    VIEW_OWN_SALARY_REPORT("View own salary(daily / monthly) report"),

    // ===== WORK MANAGEMENT =====
    VIEW_WORK_TYPES("View available work types"),
    VIEW_WORK_PRICES("View work prices"),

    // ===== MANAGER =====
    VIEW_ALL_WORKS("View employees completed works"),
    UPDATE_WORK("Update work"),
    CREATE_WORK("Create new work"),
    APPROVE_WORK("approve completed works by employees"),
    REJECT_WORK("reject uncompleted or wrong works"),
    DELETE_WORK("delete work"),

    MANAGE_WORK_TYPES("Create, update, delete work types"),
    MANAGE_WORK_PRICES("Update work prices"),
    VIEW_DAILY_ATTENDANCE_REPORT("View daily employee's attendance reports"),

    // ===== BOSS =====
    VIEW_FINANCIAL_REPORTS("View financial reports"),
    APPROVE_PAYMENTS("Approve salary payments"),
    VIEW_ALL_EMPLOYEES("View all employees"),
    VIEW_MONTHLY_SALARY_REPORT("View monthly employees salaries report"),
    VIEW_ALL_ORDERS("View all active orders list"),

    // ===== EMPLOYEE MANAGEMENT =====
    ADD_EMPLOYEE("Add new employee"),
    UPDATE_EMPLOYEE("Update employee data"),
    DELETE_EMPLOYEE("Delete employee"),

    // ===== PRODUCT MANAGEMENT =====
    CREATE_PRODUCT("Create new product"),
    UPDATE_PRODUCT("update product"),
    DELETE_PRODUCT("Delete product"),

    // ===== CUSTOMER =====
    VIEW_PRODUCTS("View products"),
    CREATE_ORDER("Create order"),
    VIEW_OWN_ORDERS("View own orders"),
    UPDATE_OWN_ORDER("update own order"),

    // ===== SYSTEM / ADMIN =====
    MANAGE_USERS("Manage system users (block / unblock user)"),
    MANAGE_ROLES("Manage roles (create / update / delete role)"),
    ASSIGN_ROLES("Assign roles to users"),

    // ===== SUPER ADMIN =====
     FULL_ACCESS("Full system access");

    private final String description;

    DefaultPermission(String description) {
        this.description = description;
    }
}
