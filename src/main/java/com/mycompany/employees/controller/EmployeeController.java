package com.mycompany.employees.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.mycompany.employees.repository.EmployeeRepository;
import com.mycompany.employees.exception.ResourceNotFoundException;
import com.mycompany.employees.model.Employee;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
		
	}
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}
	
	//get employee by id
/*	@GetMapping("/employees/{id}")
	public Employee getEmployeeById(@PathVariable Long id){
		return employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("employee with id:" +id + " does not exist in the DB"));
		
	}*/
	
	//or
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
		Employee employee =  employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("employee with id:" +id + " does not exist in the DB"));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee NewTmpEmployee, @PathVariable Long id) {
		//return this.employeeRepository.save(employee);
		Employee employee =  employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("employee with id:" +id + " does not exist in the DB"));
		employee.setFirstName(NewTmpEmployee.getFirstName());
		employee.setLastName(NewTmpEmployee.getLastName());
		employee.setEmailId(NewTmpEmployee.getEmailId());
		Employee updatedEmployee = this.employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
		
	}
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee =  employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("employee with id:" +id + " does not exist in the DB"));
		this.employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleled", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
