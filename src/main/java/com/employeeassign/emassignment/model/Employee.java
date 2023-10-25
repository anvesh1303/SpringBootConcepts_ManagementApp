package com.employeeassign.emassignment.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@JacksonXmlRootElement
@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator = "UUID")
    private Integer id;
    @NotNull(message = "firstname cannot be null")
    @Size(min = 1, max = 20)
    private String firstName;
    @Column(name = "last_name")
    private String lastname;
    private int salary;
}
