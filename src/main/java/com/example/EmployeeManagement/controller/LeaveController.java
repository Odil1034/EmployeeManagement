package com.example.EmployeeManagement.controller;

public class LeaveController {

    /*API misollari:

    Leave yaratish (employee uchun)
    POST /api/v1/leaves → employee, startDate, endDate, reason yuboradi.

    Leave ro‘yhatini olish (HR/admin)
    GET /api/v1/leaves?status=PENDING

    Leave tasdiqlash/reject qilish
    PATCH /api/v1/leaves/{id}/approve
    PATCH /api/v1/leaves/{id}/reject

    Leave tasdiqlangach, Attendance bilan bog‘lash mumkin:

    Agar employee leave kunida checkIn qilmasa, status ON_LEAVE bo‘ladi.

    Shu orqali reporting va payroll oson bo‘ladi.*/
}
