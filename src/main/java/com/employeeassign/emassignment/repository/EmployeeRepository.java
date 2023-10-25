package com.employeeassign.emassignment.repository;

import com.employeeassign.emassignment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFirstName(String firstName);
    List<Employee> findBySalaryGreaterThanEqual(Integer salary);
}

//sast test, dast test, sonar plugin
