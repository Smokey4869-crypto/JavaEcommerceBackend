package com.minh.ecommerce.inventory.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 

import com.minh.ecommerce.inventory.domain.InsufficientInventoryException;
import com.minh.ecommerce.inventory.domain.InventoryNotFoundException;

@RestControllerAdvice
public class InventoryExceptionHandler {
    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<String> handleInsufficientInventoryException(InsufficientInventoryException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<String> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
