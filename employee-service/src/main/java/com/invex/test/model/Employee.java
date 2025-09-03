
package com.invex.test.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Employee para gestion de empleados.
 */
@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String middleName;
    private String lastNameFather;
    private String lastNameMother;
    private Integer age;
    private String gender;
    private LocalDate birthDate; // formato dd-MM-yyyy solo como salida en DTO
    private String position;
    private LocalDateTime registrationDate; 
    private Boolean active;

    @PrePersist
    protected void onCreate() {
        // timestamp automatico
        this.registrationDate = LocalDateTime.now();
    }
}
