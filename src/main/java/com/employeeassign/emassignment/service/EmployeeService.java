package com.employeeassign.emassignment.service;

import com.employeeassign.emassignment.Exceptions.HeaderNotFoundException;
import com.employeeassign.emassignment.Exceptions.NameNullException;
import com.employeeassign.emassignment.model.Employee;
import com.employeeassign.emassignment.model.IncrementSalaryRequest;
import com.employeeassign.emassignment.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {
//    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Autowired
    EmployeeService(){

    }
    private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    public ResponseEntity<Page<Employee>> getEmployees(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        if (employeePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeePage);
    }


    @Transactional(rollbackFor = HeaderNotFoundException.class, isolation = Isolation.READ_COMMITTED, noRollbackFor = NullPointerException.class)
    public ResponseEntity<?> postEmployee(@Valid Employee employee){
        if(employee.getLastname().isBlank()){
            logger.error("Last Name is null");
            throw new NameNullException("Last name cannot be null");
        }
        logger.info("Employee record saved to db: {}",employee);
        var employeeResponse = employeeRepository.save(employee);
//        if(true){
//            throw new RuntimeException("Testing spring transaction");
//        }
        return ResponseEntity.created(URI.create("http://localhost:8080/employee/"+employeeResponse.getId().toString())).body("Employee created succesfully");
        //return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
    }

    public ResponseEntity<Employee> getEmployeeById(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new NameNullException("no employee");
//            logger.error("No employee with id: {}",id);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    public ResponseEntity<Employee> getEmployeeByFirstNameandLastName(String firstName, String lastName) {
        List<Employee> employees = employeeRepository.findByFirstName(firstName);
        Optional<Employee> employeeOpt = employees.stream()
                .filter(employeeobj -> employeeobj.getLastname().equals(lastName))
                .findFirst();

        if (employeeOpt.isPresent()) {
            return new ResponseEntity<>(employeeOpt.get(), HttpStatus.FOUND);
        } else {
            logger.error("No employee found with firstName: {} and lastName: {}",firstName, lastName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateEmployee(Integer id, Employee employee) {
        Optional<Employee> existingOpt = employeeRepository.findById(id);
        if(existingOpt.isEmpty()){
            logger.error("No employee found with id: {}",id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Employee existingEmployee = existingOpt.get();
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastname(employee.getLastname());
        existingEmployee.setSalary(employee.getSalary());
        var employeeResponse = employeeRepository.save(existingEmployee);
        return ResponseEntity.ok(URI.create("http://localhost:8080/employee/"+employeeResponse.getId()));
    }

    public ResponseEntity<Employee> incrementSalary(Integer id, IncrementSalaryRequest incrementSalaryRequest) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if(optEmployee.isEmpty()){
            logger.error("No employee with id: {}",id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Employee existingEmployee = optEmployee.get();
        existingEmployee.setSalary(existingEmployee.getSalary()+incrementSalaryRequest.getAmount());
        employeeRepository.save(existingEmployee);
        return new ResponseEntity<>(existingEmployee, HttpStatus.OK);
    }

    @Cacheable(cacheNames = "employeeCache")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }
}
