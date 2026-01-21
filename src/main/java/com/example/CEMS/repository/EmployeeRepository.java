package com.example.CEMS.repository;

import com.example.CEMS.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //  Find employee using linked user username
    Optional<Employee> findByUserUsername(String username);
}
