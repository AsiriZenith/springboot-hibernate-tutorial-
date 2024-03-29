package com.restful.webservice.restfulwebapp.service.Impl;

import com.restful.webservice.restfulwebapp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.restful.webservice.restfulwebapp.model.Employee;
import com.restful.webservice.restfulwebapp.repository.EmployeeRepository;
import com.restful.webservice.restfulwebapp.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployeeDetails() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {

//		Optional<Employee> employee = employeeRepository.findById(id);
//		if (employee.isPresent()) {
//			return employee.get();
//		} else {
//			throw new ResourceNotFoundException("Employee","Id",id);
//		}

		return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee","Id",id));
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {

		// we need to check whether employee with given id is exist in DB or not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
												()->new ResourceNotFoundException("Employee","Id",id));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());

		// save existing employee to DB
		employeeRepository.save(existingEmployee);

		return existingEmployee;
	}

	@Override
	public void deleteEmployee(long id) {

		// we need to check whether employee with given id is exist in DB or not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Employee","Id",id));

		employeeRepository.deleteById(id);
	}

}
