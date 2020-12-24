package com.rahulhegde.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

	@Override
	public List<Employee> findAll() {
		//Session currentSession = (Session) entityManager.unwrap(Session.class);
		TypedQuery<Employee> query = entityManager.createQuery("from Employee", Employee.class);
		List<Employee> employees = query.getResultList();
		for (Employee employee : employees) {
			System.out.println("Employees - " + employee);
		}
		return employees;
	}

	@Override
	public Employee findById(int employeeId) {
//		Session currentSession = (Session) entityManager.unwrap(Session.class);
//		Query <Employee> query = currentSession.createQuery("from Employee e where e.id=:EmployeeId", Employee.class);
//		query.setParameter("EmployeeId", employeeId);
//		Employee employee = query.getSingleResult();
//		System.out.println("Employee - " + employee);
		return entityManager.find(Employee.class, employeeId);
	}

	@Override
	public Employee add(Employee employee) {
//		Session currentSession = (Session) entityManager.unwrap(Session.class);
		// replaces saves
		entityManager.persist(employee);
//		currentSession.close();
		return employee;
	}
	
	@Override
	public Employee update(Employee employee) {
//		Session currentSession = (Session) entityManager.unwrap(Session.class);
		// replaces saveorupdate
		entityManager.merge(employee);
//		currentSession.close();
		return employee;
	}
	
	@Override
	public void deleteById(int employeeId) {
		//Session currentSession = (Session) entityManager.unwrap(Session.class);
		Query query = (Query) entityManager.createQuery("delete from Employee where id=:EmployeeId");
		query.setParameter("EmployeeId", employeeId);	
		query.executeUpdate();
		
		// Option 2: instead of delete
		// find and then remove using currentSession.remove(entity);
	}
}
