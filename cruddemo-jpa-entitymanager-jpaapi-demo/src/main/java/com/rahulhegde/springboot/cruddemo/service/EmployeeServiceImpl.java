package com.rahulhegde.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rahulhegde.springboot.cruddemo.dao.EmployeeDAO;
import com.rahulhegde.springboot.cruddemo.entity.Employee;

// Service is specialized annotation of @Component. 
// Provides Business Implementation using DAO Object. Adheres to Design Pattern - Separation of Concerns

// Responsibilities - 
// Service implements business service rules using DAOs.
// DAO is for CRUD Operation on Entity 
// Entity representing the Domain Object or the Table

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO myEmployeeDAO;
	
	@Override
	@Transactional
	public List<Employee> findAll() {
		return myEmployeeDAO.findAll();
	}

	@Transactional
	@Override
	public Employee findById(int employeeId) {
		return myEmployeeDAO.findById(employeeId);
	}

	@Override
	@Transactional
	public Employee add(Employee employee) {
		// ensure the identity value is zero else it would treat it as detached
		// persistent object
		employee.setId(0);
		return myEmployeeDAO.add(employee);
	}

	@Override
	@Transactional
	public Employee update(Employee employee) {
		return myEmployeeDAO.update(employee);
	}

	@Transactional
	@Override
	public void deleteById(int employeeId) {
		myEmployeeDAO.deleteById(employeeId);
	}
}
