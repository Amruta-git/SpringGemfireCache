package com.jp.spring.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jp.spring.model.Employee;
import com.jp.spring.model.EmployeeResponse;
import com.jp.spring.service.EmployeeDataServices;

@RestController
@RequestMapping(value = { "/jp/genesis" })
public class EmployeeRestController {

	public static final Logger LOG = LoggerFactory.getLogger(EmployeeRestController.class);

	@Autowired
	private EmployeeDataServices employeeDataServices;
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> test() {
		employeeDataServices.test();
		return new ResponseEntity<String>("Cache data successfull .... ", HttpStatus.OK);
	}

	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllEmployeees() {
		LOG.debug("Inside Get /employees ");
		Long start = System.currentTimeMillis();
		Iterable<Employee> employeeList = employeeDataServices.getAllEmployees();
		Long end = System.currentTimeMillis();
		LOG.debug("Retrieved all the data from gemfire in" + (end - start) + "ms");
		return new ResponseEntity<Iterable<Employee>>(employeeList, HttpStatus.OK);
	}

	@GetMapping(value = "/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
		LOG.debug("Inside Get /getEmployeeById : Id : " + employeeId);
		Long start = System.currentTimeMillis();
		Optional<Employee> responseEmp = employeeDataServices.getEmployeeById(employeeId);
		Long end = System.currentTimeMillis();
		LOG.debug("Retrieved employee data from gemfire in" + (end - start) + "ms");

		if (null != responseEmp && responseEmp.isPresent()) {
			return new ResponseEntity<Optional<Employee>>(responseEmp, HttpStatus.OK);
		}

		return new ResponseEntity<Optional<Employee>>(responseEmp, HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveEmployeee(@RequestBody Employee employee) {
		LOG.debug("Inside Post /employees ");
		Employee newEmployee = employeeDataServices.saveEmployees(employee);
		return new ResponseEntity<Employee>(newEmployee, HttpStatus.OK);
	}

	@PostMapping(value = "/bulk/employees", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveEmployeee(@RequestBody List<Employee> employeeList) {
		LOG.debug("Inside Post /employees ");

		Iterable<Employee> newEmployeeList = employeeDataServices.saveAllEmployees(employeeList);
		return new ResponseEntity<Iterable<Employee>>(newEmployeeList, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployee() {
		Long start = System.currentTimeMillis();
		ResponseEntity<? extends ArrayList<Employee>> responseEntity = restTemplate.getForEntity(
				"http://localhost:8090/jp/genesis/employees", (Class<? extends ArrayList<Employee>>) ArrayList.class);
		Long end = System.currentTimeMillis();
		List<Employee> employee = responseEntity.getBody(); //get list of employees from DB 
		
		Long start1 = System.currentTimeMillis();
		restTemplate.postForObject("http://localhost:8080/jp/genesis/bulk/employees", employee, //insert list of employees into gemFire
				(Class<? extends ArrayList<Employee>>) ArrayList.class);
		Long end1 = System.currentTimeMillis();

		LOG.debug("Get all the employees from DB " + (end - start) + "ms");
		LOG.debug("Cached all the employees in Gemfire in " + (end1 - start1) + "ms");

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "/limit/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getLimitedEmployee() {

		Object object = restTemplate.getForObject(
				"http://localhost:8090/jp/genesis/limit/employees?offset=0&limit=100&outputformat=json", Object.class);
		System.out.println(object.getClass());

		return new ResponseEntity<Object>(object, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/employees")
	public void deleteEmployees() {
		LOG.debug("Inside Get /test1");
		Long start = System.currentTimeMillis();
		employeeDataServices.deleteAllEmployees();
		Long end = System.currentTimeMillis();
		LOG.debug("Deleted all the employees from Gemfire in" + (end - start) + "ms");
	}

}
