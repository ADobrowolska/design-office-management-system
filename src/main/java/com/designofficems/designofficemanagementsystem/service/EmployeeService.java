package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Department;
import com.designofficems.designofficemanagementsystem.model.Employee;
import com.designofficems.designofficemanagementsystem.model.User;
import com.designofficems.designofficemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    public Employee addEmployee(Employee employee) {
        employee.setUser(userService.getUser());
        return employeeRepository.save(employee);
    }

    public Employee getEmployee() {
        User user = userService.getUser();
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow();
        return employee;
    }

    public Employee getEmployee(Integer employeeId) {
        if (getEmployee().getId().equals(employeeId)) {
            return employeeRepository.findById(employeeId).orElseThrow();
        }
        throw new AccessDeniedException("No access to employee %s".formatted(employeeId));
    }

    @Transactional
    public Employee editEmployee(Employee employee) {
        Employee existed = getEmployee();
        existed.setFirstName(employee.getFirstName());
        existed.setLastName(employee.getLastName());
        return employeeRepository.save(existed);
    }

    public List<Employee> getEmployeeByDepartment(Department department) {
        return employeeRepository.findAllByDepartment(department);
    }

    public boolean checkIfEmployeeExists(Integer employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
