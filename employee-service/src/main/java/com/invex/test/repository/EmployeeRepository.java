package com.invex.test.repository;

import com.invex.test.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	java.util.List<Employee> findByFirstNameContainingIgnoreCase(String name);
}
