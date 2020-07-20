package com.jp.spring.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jp.spring.model.Employee;


@Repository
public interface EmployeeRepository extends  CrudRepository <Employee, Integer >{
	

}
