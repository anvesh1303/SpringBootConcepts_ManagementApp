package com.employeeassign.emassignment.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Global {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentException(MethodArgumentNotValidException me){
        return new ResponseEntity<>(me.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NameNullException.class)
    public ResponseEntity<String> nameNullException(NameNullException nx){
        HttpHeaders headers = new HttpHeaders();
        headers.add("customheader1","customheadervalue1");
        return new ResponseEntity<>(nx.getMessage(),headers, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HeaderNotFoundException.class)
    public ResponseEntity<String> headerNotFoundException(HeaderNotFoundException hx){
        return new ResponseEntity<>(hx.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
