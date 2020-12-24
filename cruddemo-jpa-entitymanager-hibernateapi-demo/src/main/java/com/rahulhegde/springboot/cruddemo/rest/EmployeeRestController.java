package com.rahulhegde.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rahulhegde.springboot.cruddemo.entity.Employee;
import com.rahulhegde.springboot.cruddemo.service.EmployeeService;

@RestController
public class EmployeeRestController {
	
	@Autowired
	private EmployeeService theEmployeeService;

	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		this.theEmployeeService = theEmployeeService;
	}
		
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return theEmployeeService.findAll();
	}
	
	@GetMapping("/employees/{employeeId}")
	public Employee findById(@PathVariable int employeeId) {
		return theEmployeeService.findById(employeeId);
	}
	
	@PostMapping("/employees")
	public Employee add(@RequestBody Employee employee) {
		return theEmployeeService.add(employee);
	}
	
	@PutMapping("/employees")
	public Employee update(@RequestBody Employee employee) {
		return theEmployeeService.update(employee);
	}
	
	@DeleteMapping("/employees/{employeeId}")
	public String delete(@PathVariable int employeeId) {
		theEmployeeService.deleteById(employeeId);
		return "Delete Employee Id";
	}
}
