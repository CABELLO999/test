
package com.invex.test.controller;

import com.invex.test.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;

/**
 * Pruebas unitarias basicas para EmployeeController.
 */
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetAllEmployees() throws Exception {
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        // Incluye autenticacion basica (usuario: user, password: password)
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
            .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));
    }
}
