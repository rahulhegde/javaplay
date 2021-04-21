package com.rahulhegde.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rahulhegde.springboot.cruddemo.entity.Employee;

// Repository is variation of Component that help to create bean
// The repository bean indicates a specialized version of the bean 
// that includes CRUD operations. This would inject the data source 
// manager (entity manager) to provide data source (session).
@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<Employee> findAll() {
		Session currentSession = (Session) entityManager.unwrap(Session.class);
		Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);

		List<Employee> employees = query.getResultList();
		
		for (Employee employee : employees) {
			System.out.println("Employees - " + employee);
		}
	
		currentSession.close();
		return employees;
	}

	@Transactional
	@Override
	public Employee findById(int employeeId) {
		Session currentSession = (Session) entityManager.unwrap(Session.class);
		Query <Employee> query = currentSession.createQuery("from Employee e where e.id=:EmployeeId", Employee.class);
		query.setParameter("EmployeeId", employeeId);
		Employee employee = query.getSingleResult();
		System.out.println("Employee - " + employee);
		currentSession.close();
		return employee;
	}

	@Transactional
	@Override
	public Employee add(Employee employee) {
		Session currentSession = (Session) entityManager.unwrap(Session.class);
		currentSession.save(employee);
		currentSession.close();
		return employee;
	}
	
	@Transactional
	@Override
	public Employee update(Employee employee) {
		Session currentSession = (Session) entityManager.unwrap(Session.class);
		currentSession.update(employee);
		currentSession.close();
		return employee;
	}
	
	@Transactional
	@Override
	public void deleteById(int employeeId) {
		Session currentSession = (Session) entityManager.unwrap(Session.class);
		Query<?> query = currentSession.createQuery("delete from Employee where id=:EmployeeId");
		query.setParameter("EmployeeId", employeeId);	
		query.executeUpdate();
		currentSession.close();
	}
}
