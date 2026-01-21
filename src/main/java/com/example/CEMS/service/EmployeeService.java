package com.example.CEMS.service;

import com.example.CEMS.entity.Employee;
import com.example.CEMS.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    //  Create employee
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    //  Fetch all employees
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    //  Fetch employee ID using username
    public Long getEmployeeIdByUsername(String username) {
        return repository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found for user: " + username))
                .getId();
    }

    //  Fetch employee by ID
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));
    }

    //  Update employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existing = getEmployeeById(id);

        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setDepartment(updatedEmployee.getDepartment());

        return repository.save(existing);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
