package com.jp.spring.model;

import java.util.List;

import com.jp.spring.model.Employee;

public class EmployeeResponse {
	
	private  List<List<String>>  valueList;

	private List<Employee> employeeList;
	
	public List<List<String>> getValueList() {
		return valueList;
	}

	public void setValueList(List<List<String>> valueList) {
		this.valueList = valueList;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

}
