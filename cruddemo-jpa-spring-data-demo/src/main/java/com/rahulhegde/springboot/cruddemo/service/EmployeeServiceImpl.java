package com.rahulhegde.springboot.cruddemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.rahulhegde.springboot.cruddemo.entity.Employee;
import com.rahulhegde.springboot.cruddemo.repository.EmployeeRepository;

// Service is specialized annotation of @Component. 
// Provides Business Implementation using DAO Object. Adheres to Design Pattern - Separation of Concerns

// Responsibilities - 
// Service implements business service rules using DAOs.
// DAO is for CRUD Operation on Entity 
// Entity representing the Domain Object or the Table

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository myEmployeeRepository;
	
	@Override
	@Transactional
	public List<Employee> findAll() {
		return myEmployeeRepository.findAll();
	}

	@Transactional
	@Override
	public Employee findById(int employeeId) {
		Optional<Employee>  employee = myEmployeeRepository.findById(employeeId);
		return employee.get();
	}

	@Override
	@Transactional
	public Employee add(Employee employee) {
		// ensure the identity value is zero else it would treat it as detached
		// persistent object
		employee.setId(0);
		return myEmployeeRepository.save(employee);
	}

	@Override
	@Transactional
	public Employee update(Employee employee) {
		return myEmployeeRepository.save(employee);
	}

	@Transactional
	@Override
	public void deleteById(int employeeId) {
		myEmployeeRepository.deleteById(employeeId);
	}
}
