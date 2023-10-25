package com.employeeassign.emassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
public class EmassignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmassignmentApplication.class, args);
	}

}
