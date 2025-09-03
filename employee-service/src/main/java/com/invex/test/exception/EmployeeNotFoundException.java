package com.invex.test.exception;

/**
 * Excepcion personalizada para empleado no encontrado.
 */
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with id " + id + " not found");
    }
}
