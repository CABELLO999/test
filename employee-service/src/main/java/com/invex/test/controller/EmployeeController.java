
package com.invex.test.controller;

import com.invex.test.model.Employee;
import com.invex.test.dto.EmployeeDTO;
import com.invex.test.repository.EmployeeRepository;
import com.invex.test.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Obtiene todos los empleados como DTO.
     */
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeController::toDTO)
                .toList();
    }

    /**
     * Crea uno o varios empleados desde DTO.
     */
    @PostMapping
    public List<EmployeeDTO> createEmployees(@RequestBody List<EmployeeDTO> employeeDTOs) {
        if (employeeDTOs == null || employeeDTOs.isEmpty()) {
            throw new IllegalArgumentException("Employee list cannot be empty");
        }
        List<Employee> employees = employeeDTOs.stream().map(EmployeeController::toEntity).toList();
        List<Employee> saved = employeeRepository.saveAll(employees);
        return saved.stream().map(EmployeeController::toDTO).toList();
    }

    /**
     * Crea un solo empleado desde DTO (compatibilidad).
     */
    @PostMapping("/single")
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = toEntity(employeeDTO);
        Employee saved = employeeRepository.save(employee);
        return toDTO(saved);
    }

    /**
     * Obtiene un empleado por id como DTO.
     */
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return toDTO(employee);
    }

    /**
     * Actualiza todos o algunos campos de un empleado usando DTO.
     */
    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        if (employeeDTO.getFirstName() != null) employee.setFirstName(employeeDTO.getFirstName());
        if (employeeDTO.getMiddleName() != null) employee.setMiddleName(employeeDTO.getMiddleName());
        if (employeeDTO.getLastNameFather() != null) employee.setLastNameFather(employeeDTO.getLastNameFather());
        if (employeeDTO.getLastNameMother() != null) employee.setLastNameMother(employeeDTO.getLastNameMother());
        if (employeeDTO.getAge() != null) employee.setAge(employeeDTO.getAge());
        if (employeeDTO.getGender() != null) employee.setGender(employeeDTO.getGender());
        if (employeeDTO.getBirthDate() != null) employee.setBirthDate(employeeDTO.getBirthDate());
        if (employeeDTO.getPosition() != null) employee.setPosition(employeeDTO.getPosition());
        if (employeeDTO.getActive() != null) employee.setActive(employeeDTO.getActive());
        Employee updated = employeeRepository.save(employee);
        return toDTO(updated);
    }

    /**
     * Elimina un empleado por id.
     */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Busca empleados por nombre (busqueda parcial).
     */
    @GetMapping("/search")
    public List<EmployeeDTO> searchEmployeesByName(
            @org.springframework.web.bind.annotation.RequestParam("name") String name) {
        List<Employee> employees = employeeRepository.findByFirstNameContainingIgnoreCase(name);
        return employees.stream().map(EmployeeController::toDTO).toList();
    }

    /**
     * metodos toDTO y toEntity
     */




    private static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setLastNameFather(employee.getLastNameFather());
        dto.setLastNameMother(employee.getLastNameMother());
        dto.setAge(employee.getAge());
        dto.setGender(employee.getGender());
        dto.setBirthDate(employee.getBirthDate());
        dto.setPosition(employee.getPosition());
        dto.setActive(employee.getActive());
        return dto;
    }

  
    private static Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setMiddleName(dto.getMiddleName());
        employee.setLastNameFather(dto.getLastNameFather());
        employee.setLastNameMother(dto.getLastNameMother());
        employee.setAge(dto.getAge());
        employee.setGender(dto.getGender());
        employee.setBirthDate(dto.getBirthDate());
        employee.setPosition(dto.getPosition());
        employee.setActive(dto.getActive());
        return employee;
    }
}
