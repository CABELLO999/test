
package com.invex.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO configurado con lombok
 */

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO configurado con lombok
 */
@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastNameFather;
    private String lastNameMother;
    private Integer age;
    private String gender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private String position;
    private Boolean active;
}
