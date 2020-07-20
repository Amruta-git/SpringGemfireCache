package com.jp.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.spring.model.Employee;
import com.jp.spring.repository.EmployeeRepository;


@Service
//@Transactional
public class EmployeeDataServices {
	public static final Logger LOG = LoggerFactory.getLogger(EmployeeDataServices.class);


	@Autowired	
	EmployeeRepository employeeRepository;


	@SuppressWarnings("deprecation")
	public void test(){
        System.out.println("Spring Data GemFire example");
        
		Employee employee1 = new Employee(10010, new Date(1963,6,1), "Duangkaew", "Piveteau", "F", new Date(1989,8,24));
		Employee employee2 = new Employee(10011, new Date(1953,11,7), "Mary", "Sluis", "F", new Date(1990,1,22));         

         
        // create
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
         
        // read
		employeeRepository.findAll().forEach(emp -> LOG.debug(emp.toString()));
	}
	
	public Employee saveEmployees(Employee employee){
		Employee newEmployee = employeeRepository.save(employee);
		return newEmployee;
	}
//	public Iterable<Employee> saveAllEmployees(Iterable<Employee> employeeList){
//		Iterable<Employee> newEmployeeList = employeeRepository.saveAll(employeeList);
//		return newEmployeeList;
//	}
	
	public Iterable<Employee> saveAllEmployees(List<Employee> employeeList){
		Iterable<Employee> newEmployeeList = employeeRepository.saveAll(employeeList);
		return newEmployeeList;
	}	

	public Iterable<Employee> getAllEmployees(){
		Iterable<Employee> employeeList = employeeRepository.findAll();
		return employeeList;

	}
	
	public Optional<Employee> getEmployeeById(Integer employeeId){

		Optional<Employee> employee = employeeRepository.findById(employeeId);
		return employee;

	}

	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}



}
