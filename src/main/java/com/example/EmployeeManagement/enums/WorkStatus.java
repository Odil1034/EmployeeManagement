package com.example.EmployeeManagement.enums;

import lombok.Getter;

@Getter
public enum WorkStatus {
    PENDING("employee qo‘shgan, sex boshlig'i tasdiqlamagan"),
    APPROVED("Ish bajarilgan va sex boshlig'i tasdiqlagan"),   // ish bajarilgan va tasdiqlangan
    REJECTED("ish noto‘g‘ri bajarilgan/bajarilmagan, rad etilgan");   // ish bajarilmagan yoki noto‘g‘ri, rad etilgan

    private final String description;

    WorkStatus(String description) {
        this.description = description;
    }

}