package com.employeeassign.emassignment.controller;

import com.employeeassign.emassignment.annotation.LogExecutionTime;
import com.employeeassign.emassignment.model.Employee;
import com.employeeassign.emassignment.model.IncrementSalaryRequest;
import com.employeeassign.emassignment.service.EmployeeService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "EMSystem", description = "Employee Mgmt API's")
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @LogExecutionTime
    @Operation(summary = "Get All Employees", tags = {"get", "emsystem"})
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Employee.class))}),
        @ApiResponse(responseCode = "204", description = "No Employees Found", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping(produces = {"application/json", MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Page<Employee>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return employeeService.getEmployees(PageRequest.of(page, size, Sort.by("id")));
    }



    @PostMapping(consumes = {"application/json", MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> postEmployee(@Valid @RequestBody Employee employee){
        return employeeService.postEmployee(employee);
    }
    @GetMapping(value = "/{id}", produces = {"application/json", MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employee> getEmployeeById(Integer id){
        return employeeService.getEmployeeById(id);
    }


    @GetMapping(value = "/getAll",produces = {"application/json", MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/find")
    public ResponseEntity<Employee> getEmployeeByFirstNameandLastName
            (@RequestParam String firstName, @RequestParam String lastName){
        return employeeService.getEmployeeByFirstNameandLastName(firstName, lastName);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee){
        return employeeService.updateEmployee(id, employee);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> incrementSalary(@PathVariable Integer id, @RequestBody IncrementSalaryRequest incrementSalaryRequest){
        return employeeService.incrementSalary(id, incrementSalaryRequest);
    }


}
