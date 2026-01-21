package com.example.CEMS.controller;

import com.example.CEMS.entity.Attendance;
import com.example.CEMS.service.AttendanceService;
import com.example.CEMS.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;   //  Injected

    // Constructor Injection
    public AttendanceController(
            AttendanceService attendanceService,
            EmployeeService employeeService
    ) {
        this.attendanceService = attendanceService;
        this.employeeService = employeeService;
    }

    //  Employee Check-in
    @PostMapping("/check-in")
    public Attendance checkIn(Authentication auth) {
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Admin is not allowed to mark attendance");
        }

        String username = auth.getName();   // "user1"

        //  Convert username → employeeId properly
        Long employeeId = employeeService.getEmployeeIdByUsername(username);

        return attendanceService.checkIn(employeeId);
    }

    // Employee Check-out
    @PostMapping("/check-out")
    public Attendance checkOut(Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new RuntimeException("Admin is not allowed to mark attendance");
        }

        String username = auth.getName();
        Long employeeId = employeeService.getEmployeeIdByUsername(username);

        return attendanceService.checkOut(employeeId);
    }

    // Attendance History
    @GetMapping("/history")
    public List<Attendance> history(Authentication auth) {

        String username = auth.getName();
        Long employeeId = employeeService.getEmployeeIdByUsername(username);

        return attendanceService.getHistory(employeeId);
    }
}
