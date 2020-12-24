package com.rahulhegde.springboot.cruddemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahulhegde.springboot.cruddemo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
