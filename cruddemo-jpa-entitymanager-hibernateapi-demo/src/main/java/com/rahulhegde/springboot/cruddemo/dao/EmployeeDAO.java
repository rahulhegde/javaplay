package com.rahulhegde.springboot.cruddemo.dao;

import java.util.List;

import com.rahulhegde.springboot.cruddemo.entity.Employee;

public interface EmployeeDAO {
	public List<Employee> findAll();
	
	public Employee findById(int employeeId);
	
	public Employee add(Employee employee);

	public Employee update(Employee employee);

	public void deleteById(int employeeId);
}
