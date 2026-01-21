package com.example.CEMS.service;

import com.example.CEMS.entity.Attendance;
import com.example.CEMS.entity.AttendanceStatus;
import com.example.CEMS.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    //  Check-In
    public Attendance checkIn(Long employeeId) {

        LocalDate today = LocalDate.now();

        attendanceRepository.findByEmployeeIdAndDate(employeeId, today)
                .ifPresent(a -> {
                    throw new RuntimeException("Already checked in today!");
                });

        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.PRESENT);

        return attendanceRepository.save(attendance);
    }

    //  Check-Out
    public Attendance checkOut(Long employeeId) {

        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No check-in found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Already checked out!");
        }

        attendance.setCheckOutTime(LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    // Attendance History
    public List<Attendance> getHistory(Long employeeId) {
        return attendanceRepository.findByEmployeeIdOrderByDateDesc(employeeId);
    }
}
